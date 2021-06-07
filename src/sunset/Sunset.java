package sunset;

import arc.*;
import arc.func.*;
import arc.util.*;
import mindustry.ctype.*;
import mindustry.Vars;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import sunset.content.*;
import sunset.content.blocks.*;
import mindustry.game.EventType.FileTreeInitEvent;
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
    

    public Sunset() {
        if (Vars.headless) {
            Events.on(FileTreeInitEvent.class, e -> {
                SnSounds.load();
            });
        } else {
            SnSounds.load();
        }
    }

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