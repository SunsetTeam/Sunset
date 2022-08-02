package sunset.world.blocks.defense.turrets;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import sunset.entities.bullet.FlowerBulletType;

public class FlowerTurret extends ItemTurret {
    public FlowerTurret(String name) {
        super(name);
    }

    public class FlowerTurretBuild extends ItemTurretBuild {
        @Override
        protected void shoot(BulletType rawType) {
            FlowerBulletType type = (FlowerBulletType) rawType;
            float bulletX = x + Angles.trnsx(rotation - 90, shootX, shootY),
                  bulletY = y + Angles.trnsy(rotation - 90, shootX, shootY);

            if(shoot.firstShotDelay > 0){
                chargeSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
                type.chargeEffect.at(bulletX, bulletY, rotation);
            }

            type.shoot.shoot(totalShots, (xOffset, yOffset, angle, delay, mover) -> {
                queuedBullets ++;
                if(delay > 0f){
                    Time.run(delay, () -> bullet(type, xOffset, yOffset, angle, mover));
                }else{
                    bullet(type, xOffset, yOffset, angle, mover);
                }
                totalShots ++;
            });

            if(consumeAmmoOnce){
                useAmmo();
            }
        }

        @Override
        protected void bullet(BulletType rawType, float xOffset, float yOffset, float angleOffset, Mover mover) {
            FlowerBulletType type = (FlowerBulletType) rawType;
            queuedBullets --;

            if(dead || (!consumeAmmoOnce && !hasAmmo())) return;

            float
                    xSpread = Mathf.range(xRand),
                    bulletX = x + Angles.trnsx(rotation - 90, shootX + xOffset + xSpread, shootY + yOffset),
                    bulletY = y + Angles.trnsy(rotation - 90, shootX + xOffset + xSpread, shootY + yOffset),
                    offs = angleOffset + Mathf.range(inaccuracy),
                    shootAngle = rotation + offs;

            float lifeScl = type.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, targetPos.x, targetPos.y) / type.range, minRange / type.range, range() / type.range) : 1f;

            FlowerBulletType.FlowerBulletTypeData data = new FlowerBulletType.FlowerBulletTypeData(
                offs,
                Mathf.random(),
                Mathf.random(type.rotateFactorMin, type.rotateFactorMax
            ));
            //TODO aimX / aimY for multi shot turrets?
            handleBullet(type.create(this, team, bulletX, bulletY, shootAngle, -1f, (1f - velocityRnd) + Mathf.random(velocityRnd), lifeScl, data, mover, targetPos.x, targetPos.y), xOffset, yOffset, shootAngle - rotation);

            (shootEffect == null ? type.shootEffect : shootEffect).at(bulletX, bulletY, rotation + angleOffset, type.hitColor);
            (smokeEffect == null ? type.smokeEffect : smokeEffect).at(bulletX, bulletY, rotation + angleOffset, type.hitColor);
            shootSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));

            ammoUseEffect.at(
                    x - Angles.trnsx(rotation, ammoEjectBack),
                    y - Angles.trnsy(rotation, ammoEjectBack),
                    rotation * Mathf.sign(xOffset)
            );

            if(shake > 0){
                Effect.shake(shake, shake, this);
            }

            curRecoil = 1f;
            heat = 1f;

            if(!consumeAmmoOnce){
                useAmmo();
            }
        }
    }
}
