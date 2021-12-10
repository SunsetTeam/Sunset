package sunset.core;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import mindustry.game.EventType;
import sunset.content.SnMusic;

import static mindustry.Vars.*;
import static mma.ModVars.modInfo;

public class SnSoundControl {
    private static final Seq<Music> prevAmbient = new Seq<>(), prevDark = new Seq<>(), prevBossMusic = new Seq<>();
    public static Seq<Music> snAmbientMusic = new Seq<>();
    public static Seq<Music> snDarkMusic = new Seq<>();
    public static Seq<Music> snBossMusic = new Seq<>();
    private boolean lastMapSn;

    public SnSoundControl() {

        snDarkMusic.addAll(SnMusic.acid_rain);
        snAmbientMusic.addAll(SnMusic.storm);
        snAmbientMusic.addAll(SnMusic.deceit);
        snBossMusic.addAll(SnMusic.heavy_arsenal);
        snBossMusic.addAll(SnMusic.cold_fire);
        

        Events.on(EventType.WorldLoadEvent.class, e -> {
            boolean isSn = state.map.mod != null && state.map.mod == modInfo;

            if (isSn != lastMapSn) {
                lastMapSn = !lastMapSn;
                if (isSn) {
                    replaceMusic(control.sound.ambientMusic, snAmbientMusic, prevAmbient);
                    replaceMusic(control.sound.darkMusic, snDarkMusic, prevDark);
                    replaceMusic(control.sound.bossMusic, snBossMusic, prevBossMusic);
                } else {
                    replaceMusic(control.sound.ambientMusic, prevAmbient, null);
                    replaceMusic(control.sound.darkMusic, prevDark, null);
                    replaceMusic(control.sound.bossMusic, prevBossMusic, null);
                }
            }
        });

        Events.run(EventType.ClientLoadEvent.class, this::reload);
    }


    public void reload() {

    }

    private void replaceMusic(Seq<Music> target, Seq<Music> replacement, Seq<Music> save) {
        if (save != null) {
            save.set(target);
        }
        target.set(replacement);
    }
}
