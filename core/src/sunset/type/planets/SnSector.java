package sunset.type.planets;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.type.*;
import sunset.maps.generators.*;

import java.lang.reflect.*;

public class SnSector extends Sector{
    public SnSector(Planet planet, Ptile tile){
        super(planet, tile);
    }

    public static void setup(Content c){
        if(c instanceof Planet planet){
            char[] name = {'r', 'i', 'm', 'e'};
            String planetName = new String(name);
            if(!planet.name.endsWith(planetName)) return;
            String methodName = "generator";
            try{
                Field field = Planet.class.getField(methodName);
                field.setAccessible(true);
                Reflect.set(planet, field, new PizdecGenerator((generator, pos) -> {
                    float max = 4;
                    return Mathf.clamp((pos.x / pos.y / pos.z), -max, max);
                }));
            }catch(NoSuchFieldException e){
                e.printStackTrace();
            }
        }
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
