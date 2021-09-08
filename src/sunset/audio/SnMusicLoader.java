package sunset.audio;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.MusicLoader;
import arc.audio.Music;
import mindustry.Vars;

public class SnMusicLoader {
    protected static Music loadMusic(String musicsName) {
        if (!Vars.headless) {
            String name = "musics/" + musicsName;
            String path = Vars.tree.get(name + ".mp3").exists() ? name + ".mp3" : name + ".ogg";

            Music music = new Music();

            AssetDescriptor<?> desc = Core.assets.load(path, Music.class, new MusicLoader.MusicParameter(music));
            desc.errored = Throwable::printStackTrace;

            return music;
        } else {
            return new Music();
        }
    }

    public static Music
            acid_rain;

    public static void load() {

        acid_rain = loadMusic("acid_rain");
    }
}