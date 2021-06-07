package sunset;

import arc.*;
import arc.func.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import sunset.content.*;
import sunset.content.blocks.*;
import sunset.world.MissileLogic;

public class Sunset extends Mod {
    private final ContentList[] SnContent = {
            new SnItems(),
            new SnEnvironment(),
            new SnStatusEffects(),
            new SnBullets(),
            new SnWeathers(),
            new SnDefense(),
            new SnOther(),
            new SnPower(),
            new SnTurrets(),
            new SnProduction()
    };
    

    @Override
    public void init(){
        MissileLogic.init();
    }
    
    @Override
    public void loadContent(){
        for(ContentList list : SnContent){
            list.load();
            Log.info("@: Loaded content list: @", getClass().getSimpleName(), list.getClass().getSimpleName());
        }
    }
}