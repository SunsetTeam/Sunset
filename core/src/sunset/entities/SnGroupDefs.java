package sunset.entities;

import mindustry.annotations.Annotations;
import mindustry.gen.*;
import sunset.gen.*;

class SnGroupDefs<G> {
    @Annotations.GroupDef({Deliverc.class}) G delivers;
    @Annotations.GroupDef({FireFighterc.class}) G fireFighters;
    @Annotations.GroupDef({Segmentc.class}) G segments;
    @Annotations.GroupDef({Buildingc.class}) G laserBuilds;
}
