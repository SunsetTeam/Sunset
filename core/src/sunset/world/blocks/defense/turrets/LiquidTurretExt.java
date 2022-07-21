package sunset.world.blocks.defense.turrets;

import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

/** Various add-ons for the liquid turret. */
public class LiquidTurretExt extends LiquidTurret {
    public LiquidTurretExt(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        //TODO: override ammoPerShot for liquid description
    }

    public class LiquidTurretExtBuild extends LiquidTurretBuild {
        @Override
        public BulletType useAmmo() {
            if(cheating()) return ammoTypes.get(liquids.current());
            BulletType type = ammoTypes.get(liquids.current());
            liquids.remove(liquids.current(), ammoPerShot / type.ammoMultiplier);
            return type;
        }

        @Override
        public boolean hasAmmo() {
            return ammoTypes.get(liquids.current()) != null && liquids.currentAmount() >= ammoPerShot / ammoTypes.get(liquids.current()).ammoMultiplier;
        }
    }
}
