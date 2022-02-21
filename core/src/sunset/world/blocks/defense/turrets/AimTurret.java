package sunset.world.blocks.defense.turrets;

import mindustry.gen.Posc;

public class AimTurret extends ModItemTurret {

    public AimTurret(String name) {
        super(name);
    }

    public class AimTurretBuild extends ModItemTurretBuild{

        @Override
        public void updateTile() {
            super.updateTile();
        }

        @Override
        public void targetPosition(Posc pos) {
            targetPos.set(pos.x(), pos.y());
        }
    }
}
