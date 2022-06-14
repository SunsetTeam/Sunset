package sunset.io;

import arc.util.io.*;
import mindustry.gen.*;
import mindustry.io.*;
import sunset.type.*;
import sunset.world.blocks.units.*;
import sunset.world.blocks.units.FireFightersStation.*;

public class SnTypeIO extends TypeIO{
    public static void writeMissileType(Writes write, MissileType type){
        write.i(type == null ? -1 : type.id);
    }

    public static MissileType readMissileType(Reads read){
        int i = read.i();
        if(i == -1) return null;
        return MissileType.missileTypes.get(i);
    }

    public static void writeAirportBuild(Writes write, Airport.AirportBuild airportBuild){
        writeBuilding(write, airportBuild);
    }

    public static Airport.AirportBuild readAirportBuild(Reads read){
        Building building = readBuilding(read);
        return building instanceof Airport.AirportBuild ? building.as() : null;
    }

    public static void writeFireFightersStationBuild(Writes write, FireFightersStationBuild airportBuild){
        writeBuilding(write, airportBuild);
    }

    public static FireFightersStationBuild readFireFightersStationBuild(Reads read){
        Building building = readBuilding(read);
        return building instanceof FireFightersStationBuild ? building.as() : null;
    }

    public static void writeDeliverState(Writes write, DeliverState state){
        write.s(state == null ? -1 : state.ordinal());
//        writeEnum(write,state);
    }

    public static DeliverState readDeliverState(Reads read){
        short s = read.s();
        return s == -1 ? null : DeliverState.values()[s];
    }
}
