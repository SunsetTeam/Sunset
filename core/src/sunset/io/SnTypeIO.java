package sunset.io;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.gen.Building;
import mma.io.ModTypeIO;
import sunset.type.DeliverState;
import sunset.type.blocks.MissileType;
import sunset.world.blocks.units.Airport;

@Annotations.TypeIOHandler
public class SnTypeIO extends ModTypeIO {
    public static void writeMissileType(Writes write, MissileType type) {
        write.i(type == null ? -1 : type.id);
    }

    public static MissileType readMissileType(Reads read) {
        int i = read.i();
        if (i == -1) return null;
        return MissileType.missileTypes.get(i);
    }

    public static void writeAirportBuild(Writes write, Airport.AirportBuild airportBuild) {
        writeBuilding(write, airportBuild);
    }

    public static Airport.AirportBuild readAirportBuild(Reads read) {
        Building building = readBuilding(read);
        return building instanceof Airport.AirportBuild ? building.as() : null;
    }
    public static void writeDeliverState(Writes write, DeliverState state){
        writeEnum(write,state);
    }
    public static DeliverState readDeliverState(Reads read){
        return readEnum(read,DeliverState.values());
    }
}
