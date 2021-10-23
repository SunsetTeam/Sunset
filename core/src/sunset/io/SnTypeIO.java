package sunset.io;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mma.io.ModTypeIO;
import sunset.type.MissileType;

@Annotations.TypeIOHandler
public class SnTypeIO extends ModTypeIO {
    public static void writeMissileType(Writes write, MissileType type){
        write.i(type==null?-1:type.id);
    }
    public  static MissileType readMissileType(Reads read){
        int i = read.i();
        if (i==-1)return null;
        return MissileType.missileTypes.get(i);
    }
}
