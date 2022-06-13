package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.Color;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;

/** Laser source. Very raw*/
public class LaserSource extends LaserNode{
    public LaserSource(String name) {
        super(name);
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("chargeBar", (LaserBlockBuild entity) ->
                new Bar(() -> Core.bundle.format("bar.laser-output", entity.laser.out),
                        () -> Pal.powerBar,
                        () -> 1f));
    }
    public class LaserSourceBuild extends LaserNodeBuild{
        @Override
        public float getLaserProduction(){
            return 5f;
        }
    }
}
