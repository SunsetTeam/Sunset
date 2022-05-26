package sunset.world.consumers;

import mindustry.gen.*;
import mindustry.world.consumers.*;

public class TimeScaleConsume extends Consume{
    @Override
    public float efficiencyMultiplier(Building build){
        return build.timeScale();
    }

    @Override
    public Consume optional(boolean optional, boolean boost){
        return super.optional(optional, boost);
    }

    @Override
    public float efficiency(Building build){
        return build.timeScale();
    }
}
