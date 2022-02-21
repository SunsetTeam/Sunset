package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.logic.Ranged;

public class ReverseBulletType extends BasicBulletType {
    public float rotateTotalAngle = 360;
    public boolean rotateRight = true;
    public float rotateMag = 0f, rotScaling = 0, rotScaleMin = 0, rotScaleMax = 0, rotateVisualMag = 0;
    public boolean reverseRotScale = false, stayInRange = false;
    public BulletType other = null;


    public ReverseBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage);
        sprite = bulletSprite;
        hittable = false;
        reflectable = false;
        absorbable = false;
    }

    public ReverseBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }

    @Override
    public void draw(Bullet b) {

        float height = this.height * ((1f - shrinkY) + shrinkY * b.fout());
        float width = this.width * ((1f - shrinkX) + shrinkX * b.fout());
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);
        float scaling = reverseRotScale ? b.fout() : b.fin();
        float rotation = b.rotation() + offset + rotateTotalAngle * rotateVisualMag * scaling % 360 * (rotateRight ? -1 : 1);

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

        Draw.mixcol(mix, mix.a);

        Draw.color(backColor);
        Draw.rect(backRegion, b.x, b.y, width, height, rotation);
        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, rotation);

        Draw.reset();
    }

    public void update(Bullet b) {
        super.update(b);

        if (rotateMag > 0) {
            b.vel.rotate(rotateMag * Mathf.clamp(reverseRotScale ? b.fout() : b.fin(), rotScaleMin, rotScaleMax) * (rotateRight ? -1 : 1) * Time.delta * rotScaling);
        }

        if(stayInRange && b.owner instanceof Ranged && b.dst(((Ranged) b.owner).x(), ((Ranged) b.owner).y()) > ((Ranged) b.owner).range()) b.rotation(b.angleTo(((Ranged) b.owner).x(), ((Ranged) b.owner).y()));

    }

    @Override
    public Bullet create(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data) {
        if (other != null && Mathf.randomSeed((int) Time.time * 2, 0, 1) > 0.4 && other instanceof ReverseBulletType) {
            return ((ReverseBulletType) other).createReverse(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data);
        } else return super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data);
    }

    public Bullet createReverse(@Nullable Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data) {
        return super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, data);
    }

}
