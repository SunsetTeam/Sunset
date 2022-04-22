package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.logic.Ranged;

public class ReverseBulletType extends BasicBulletType {
    public float rotateMag = 0f, rotScl = 0f, rotSclMin = 0f, rotSclMax = 0f, rotVisualMag = 0f;
    public float rotTotalAngle = 0f;
    public boolean rotRight = true;
    public boolean reverseRotScl = false, inRange = false;

    public ReverseBulletType(float speed, float damage) {
        super(speed, damage);
        hittable = false;
        reflectable = false;
        absorbable = false;
    }
    @Override
    public void draw(Bullet b) {

        float height = this.height * ((1f - shrinkY) + shrinkY * b.fout());
        float width = this.width * ((1f - shrinkX) + shrinkX * b.fout());
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);
        float scaling = reverseRotScl ? b.fout() : b.fin();
        float rotation = b.rotation() + offset + rotTotalAngle * rotVisualMag * scaling % 360 * (rotRight ? -1 : 1);

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
            b.vel.rotate(rotateMag * Mathf.clamp(reverseRotScl ? b.fout() : b.fin(), rotSclMin, rotSclMax) * (rotRight ? -1 : 1) * Time.delta * rotScl);
        }

        if(inRange && b.owner instanceof Ranged && b.dst(((Ranged) b.owner).x(), ((Ranged) b.owner).y()) > ((Ranged) b.owner).range()) b.rotation(b.angleTo(((Ranged) b.owner).x(), ((Ranged) b.owner).y()));
    }
}
