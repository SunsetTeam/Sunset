package sunset.audio;

import arc.audio.Music;
import arc.struct.Seq;

import static sunset.audio.SnMusicLoader.*;

public class SnMusic {
    public Seq<Music> ambientMusic = Seq.with(acid_rain);
    public Seq<Music> darkMusic = Seq.with();
    public Seq<Music> bossMusic = Seq.with();
}