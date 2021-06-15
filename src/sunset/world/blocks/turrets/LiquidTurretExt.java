package sunset.world.blocks.turrets;

import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

/** Различные дополнения для жидкостной турели. */
public class LiquidTurretExt extends LiquidTurret {
    public LiquidTurretExt(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        //TODO: переопределить ammoPerShot под описание жидкости
    }

    public class LiquidTurretExtBuild extends LiquidTurretBuild {
        @Override
        public BulletType useAmmo(){
            if(cheating()) return ammoTypes.get(liquids.current());
            BulletType type = ammoTypes.get(liquids.current());
            liquids.remove(liquids.current(), ammoPerShot / type.ammoMultiplier);
            return type;
        }

        @Override
        public boolean hasAmmo() {
            return ammoTypes.get(liquids.current()) != null && liquids.total() >= ammoPerShot / ammoTypes.get(liquids.current()).ammoMultiplier;
        }
    }
}
