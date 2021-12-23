package sunset.type.planets;

import arc.*;
import mindustry.game.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.type.*;

public class SnSector extends Sector{
    public SnSector(Planet planet, Ptile tile){
        super(planet, tile);
    }


    @Override
    public void loadInfo(){
        info = Core.settings.getJson(planet.name + "-s-" + id + "-info", SnSectorInfo.class, SnSectorInfo::new);
    }

    @Override
    public void clearInfo(){
        info = new SnSectorInfo();
        Core.settings.remove(planet.name + "-s-" + id + "-info");
    }
}
