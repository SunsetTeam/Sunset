package sunset.core;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.struct.*;
import mindustry.game.*;
import mindustry.type.*;
import sunset.gen.*;

import static mindustry.Vars.*;
import static mma.ModVars.modInfo;

public class SnSoundControl{
    public static final Seq<Music> snAmbientMusic = new Seq<>();
    public static final Seq<Music> snDarkMusic = new Seq<>();
    public static final Seq<Music> snBossMusic = new Seq<>();
    private boolean lastMapSn;

    public SnSoundControl(){
        new MusicField(() -> control.sound.ambientMusic, snAmbientMusic);
        new MusicField(() -> control.sound.darkMusic, snDarkMusic);
        new MusicField(() -> control.sound.bossMusic, snBossMusic);

        Events.run(EventType.WorldLoadEvent.class, this::checkState);

        Events.run(EventType.MusicRegisterEvent.class, this::reload);
    }

    public void checkState(){
        boolean isSn = isModMap();

        if(isSn != lastMapSn){
            lastMapSn = !lastMapSn;
            if(isSn){
                MusicField.fields.each(MusicField::add);
            }else{
                MusicField.fields.each(MusicField::remove);
            }
        }
    }

    private boolean isModMap(){
        Sector sector = state.getSector();
        if(sector != null){
            return sector.planet.minfo.mod == modInfo;
        }
        return state.map != null && state.map.mod != null && state.map.mod == modInfo;
    }


    public void reload(){

        snDarkMusic.addAll(SnMusics.acid_rain);
        snAmbientMusic.addAll(SnMusics.storm, SnMusics.deceit, SnMusics.deceit);
        snBossMusic.addAll(SnMusics.heavy_arsenal, SnMusics.cold_fire);

    }

    private static class MusicField{
        public static final Seq<MusicField> fields = new Seq<>();
        private final Prov<Seq<Music>> target;
        private final Prov<Seq<Music>> replacement;
        private final Seq<Music> save = new Seq<>();

        public MusicField(Prov<Seq<Music>> target, Prov<Seq<Music>> replacement){
            this.target = target;
            this.replacement = replacement;
            fields.add(this);
        }

        public MusicField(Prov<Seq<Music>> target, Seq<Music> replacement){
            this(target, () -> replacement);
        }

        public void add(){

            save.set(target.get());
            target.get().set(replacement.get());
        }

        public void remove(){
            target.get().set(save);
            save.clear();
        }
    }
}
