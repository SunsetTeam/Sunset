package sunset.core;

import arc.Events;
import arc.audio.Music;
import arc.struct.Seq;
import mindustry.game.EventType;

import static mindustry.Vars.*;
import static mma.ModVars.modInfo;

public class SnSoundControl {
    private static final Seq<Music> prevAmbient = new Seq<>(), prevDark = new Seq<>();
    public static Seq<Music> snAmbientMusic = new Seq<>();
    public static Seq<Music> snDarkMusic = new Seq<>();
    private boolean lastMapSn;

    public SnSoundControl() {
        Events.on(EventType.WorldLoadEvent.class, e -> {
            boolean isSn = state.map.mod != null && state.map.mod == modInfo;

            if (isSn != lastMapSn) {
                lastMapSn = !lastMapSn;
                if (isSn) {
                    replaceMusic(control.sound.ambientMusic, snAmbientMusic, prevAmbient);
                    replaceMusic(control.sound.darkMusic, snDarkMusic, prevDark);
                } else {
                    replaceMusic(control.sound.ambientMusic, prevAmbient, null);
                    replaceMusic(control.sound.darkMusic, prevDark, null);
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
