package sunset.type.planets;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.type.*;
import sunset.game.*;
import sunset.maps.generators.*;

import java.lang.reflect.*;

public class SnSector extends Sector{
    private SnSectorInfo snInfo;

    public SnSector(Planet planet, Ptile tile){
        super(checkPlanet(planet), tile);
        info = snInfo = new SnSectorInfo(this);
    }

    private static Planet checkPlanet(Planet planet){
        if(planet instanceof SnPlanet) return planet;
        throw new RuntimeException("Planet must be SnPlanet");
    }

    public static void setup(Content c){
        if(c instanceof Planet planet){
            char[] name = {'r', 'i', 'm', 'e'};
            String planetName = new String(name);
            if(!planet.name.endsWith(planetName)) return;
            String methodName = "generator";
            try{
                Field field = Planet.class.getDeclaredField(methodName);
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
        info = snInfo = Core.settings.getJson(planet.name + "-s-" + id + "-info", SnSectorInfo.class, SnSectorInfo::new).parent(this);
    }

    @Override
    public void clearInfo(){
        info = snInfo = new SnSectorInfo().parent(this);
        Core.settings.remove(planet.name + "-s-" + id + "-info");
    }

    public SnPlanet snPlanet(){
        return (SnPlanet)planet;
    }

    public SnWaves waves(){
        return snPlanet().waves;
    }
}
