package sunset.core;

import mindustry.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.ui.dialogs.*;
import mma.*;
import sunset.content.*;

import static mma.ModVars.modInfo;

public class SnPlanetSystem{
    static Planet prevStar;
    static Planet snStar;

    public static void setup(){
        snStar = SnPlanets.magma;
        ModListener.updaters.add(SnPlanetSystem::checkChanges);

    }

    private static void updatePlanet(Planet planet){
        planet.position.setZero();
        planet.addParentOffset(planet.position);
        if(planet.parent != null){
            planet.position.add(planet.parent.position);
        }
        for(Planet child : planet.children){
            updatePlanet(child);
        }
    }

    private static void checkChanges(){
        updatePlanet(snStar);

        PlanetParams state = Vars.ui.planet.state;
        if(state.solarSystem == snStar){
            if(state.planet.minfo.mod != modInfo){
                state.solarSystem = prevStar;
                updateDialog();
            }
        }else{
            if(state.planet.minfo.mod == modInfo){
                prevStar = state.solarSystem;
                state.solarSystem = snStar;
                updateDialog();
            }
        }

    }

    private static void updateDialog(){

        PlanetDialog dialog = Vars.ui.planet;
        if(dialog.isShown()){

            dialog.show(dialog.getScene(), null);
        }
    }
}
