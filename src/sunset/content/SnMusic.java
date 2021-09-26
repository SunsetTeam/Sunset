package sunset.content;
import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.MusicLoader;
import arc.audio.Music;
import arc.struct.*;
import mindustry.Vars;

// no more "musics"
public class SnMusic {
    protected static Music loadMusic(String musicName, boolean ambient, boolean dark){
        if (!Vars.headless) {
            String name = "music/" + musicName;
            String path = Vars.tree.get(name + ".mp3").exists() ? name + ".mp3" : name + ".ogg";

            Music music = new Music();

            AssetDescriptor<?> desc = Core.assets.load(path, Music.class, new 
            MusicLoader.MusicParameter(music));
            desc.errored = Throwable::printStackTrace;

            if(ambient) snAmbientMusic.add(music);
            if(dark) snDarkMusic.add(music);
            return music;
        } else {
            return new Music();
        }
    }

    public static Seq<Music>
        snAmbientMusic = new Seq<>(),
        snDarkMusic = new Seq<>();

    public static Music
        AcidRain;

    public void load() {
        AcidRain = loadMusic("acid_rain", true, false);
    }
}