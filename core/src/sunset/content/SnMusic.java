package sunset.content;

import arc.*;
import arc.assets.*;
import arc.assets.loaders.*;
import arc.audio.*;
import arc.struct.*;
import mindustry.*;

public class SnMusic{
    protected static Music loadMusic(String musicName){
        if (!Vars.headless){
            String name = "music/" + musicName;
            String path = Vars.tree.get(name + ".mp3").exists() ? name + ".mp3" : name + ".ogg";

            Music music = new Music();

            AssetDescriptor<?> desc = Core.assets.load(path, Music.class, new MusicLoader.MusicParameter(music));
            desc.errored = Throwable::printStackTrace;
            return music;
        }else{
            return new Music();
        }
    }


    public static Music
    Acid_Rain;

    public void load(){
        Acid_Rain = loadMusic("acid_rain");
    }
}
