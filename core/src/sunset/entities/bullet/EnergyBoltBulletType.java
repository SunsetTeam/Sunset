package sunset.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Posc;
import mindustry.graphics.Pal;
import sunset.world.blocks.defense.turrets.SynthesisTurret;

public class EnergyBoltBulletType extends LaserBoltBulletType{
    public float width = 2f, height = 7f;
    public float pierceDamageFactor = 1f;
    public float length = 100f;

    public EnergyBoltBulletType(float speed, float damage){
        super(15, 35);

        lifetime = 35;
        ammoMultiplier = 1;
        buildingDamageMultiplier = this.damage * 0.75f;
        healPercent = 0.001f;

        reflectable = true;
        absorbable = false;
        pierceBuilding = true;
        pierce = true;
        scaleVelocity = true;
        collides = true;

        shootEffect = Fx.shootHeal;
        smokeEffect = Fx.hitLaser;
        hitEffect = Fx.hitLaser;
        despawnEffect = Fx.despawn;

        backColor = Pal.heal;
        frontColor = Color.white;
    }

    public EnergyBoltBulletType(){
        this(1f, 1f);
    }

    /*void handle(Bullet b, Posc pos, float initialHealth){
        float sub = Math.max(initialHealth * pierceDamageFactor, 0);

        if(b.damage <= 0){
            b.fdata = Math.min(b.fdata, b.dst(pos));
            return;
        }

        if(b.damage > 0){
            hitEffect.at(pos.getX(), pos.getY());
        }

        b.damage -= Math.min(b.damage, sub);
    }*/

    @Override
    public void draw(Bullet b){
        super.draw(b);
        Draw.color(backColor);
        Lines.stroke(width * SynthesisTurret.param1);
        Lines.lineAngleCenter(b.x, b.y, b.rotation(), height);
        Draw.color(frontColor);
        Lines.lineAngleCenter(b.x, b.y, b.rotation(), Mathf.random(height / 4, height / 2));
        Draw.reset();
    }
}
