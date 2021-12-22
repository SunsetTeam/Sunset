package sunset.core;

import arc.audio.Music;
import mindustry.Vars;
import sunset.gen.SnMusics;


public class SnSoundControl {

    public static void reload() {
        Vars.control.sound.ambientMusic.add(SnMusics.deceit, SnMusics.storm);
        Vars.control.sound.bossMusic.add(SnMusics.heavy_arsenal, SnMusics.cold_fire);
        Vars.control.sound.darkMusic.add(SnMusics.acid_rain);
    }
}
