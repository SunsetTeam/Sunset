package sunset.core;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.type.*;
import sunset.content.*;
import sunset.gen.*;

import static mindustry.Vars.*;
import static mma.ModVars.modInfo;

public class SnSoundControl{
    public static Seq<Music> snAmbientMusic = new Seq<>();
    public static Seq<Music> snDarkMusic = new Seq<>();
    public static Seq<Music> snBossMusic = new Seq<>();
    private boolean lastMapSn;

    public SnSoundControl(){


        final Seq<Music> prevAmbient = new Seq<>(), prevDark = new Seq<>(), prevBossMusic = new Seq<>();
        Prov<Seq<Music>>[]
        replacements = new Prov[]{() -> snAmbientMusic, () -> snDarkMusic, () -> snBossMusic},
        targets = new Prov[]{() -> control.sound.ambientMusic, () -> control.sound.darkMusic, () -> control.sound.bossMusic},
        previous = new Prov[]{() -> prevAmbient, () -> prevDark, () -> prevBossMusic};
        Events.run(EventType.SaveLoadEvent.class,()->{
            Log.info("save load");
        });
        Events.on(EventType.WorldLoadEvent.class, e -> {
            Log.info("world load");
            checkState(replacements, targets, previous);
        });

        Events.run(EventType.MusicRegisterEvent.class, this::reload);
    }

    public void checkState(Prov<Seq<Music>>[] replacements, Prov<Seq<Music>>[] targets, Prov<Seq<Music>>[] previous){
        boolean isSn = isModMap();

        if(isSn != lastMapSn){
            lastMapSn = !lastMapSn;
            for(int i = 0; i < targets.length; i++){
                replaceSave(targets[i].get(), replacements[i].get(), isSn ? previous[i].get() : null);
            }
        }
    }

    private boolean isModMap(){
        Sector sector = state.getSector();
        if (sector!=null){
            return sector.planet.minfo.mod ==modInfo;
        }
        return state.map != null && state.map.mod != null && state.map.mod == modInfo;
    }


    public void reload(){

        snDarkMusic.addAll(SnMusics.acid_rain);
        snAmbientMusic.addAll(SnMusics.storm, SnMusics.deceit, SnMusics.deceit);
        snBossMusic.addAll(SnMusics.heavy_arsenal, SnMusics.cold_fire);

    }

    private <T> void replaceSave(Seq<T> target, Seq<T> replacement, Seq<T> save){
        if(save != null){
            save.set(target);
        }
        target.set(replacement);
    }
}
