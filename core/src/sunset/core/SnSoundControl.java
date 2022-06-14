package sunset.core;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.struct.*;
import mindustry.game.*;
import static mindustry.Vars.*;

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

    private boolean isModMap(){return false;
    }


    public void reload(){
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
