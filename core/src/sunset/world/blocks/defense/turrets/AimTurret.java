package sunset.world.blocks.defense.turrets;

import mindustry.gen.Posc;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class AimTurret extends ItemTurret {

    public AimTurret(String name) {
        super(name);
    }

    public class AimTurretBuild extends ItemTurretBuild{

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
