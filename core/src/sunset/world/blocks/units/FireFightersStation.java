package sunset.world.blocks.units;

import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;

public class FireFightersStation extends Block{
    public float unitBuildTime = 12 * Time.toSeconds;
    public UnitType unitType;
    public int maxUnitCount;

    public FireFightersStation(String name){
        super(name);
        hasLiquids = true;
        allowResupply = true;
    }

    public class FireFightersStationBuild extends Building{
        public Seq<Unit> units = new Seq<>();
        public Runnable afterRead=()->{};

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return liquid == Liquids.water;
        }

        @Override
        public void handleLiquid(Building source, Liquid liquid, float amount){
            if(!acceptLiquid(source, liquid)) return;
            super.handleLiquid(source, liquid, amount);
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if(consValid() && units.size < maxUnitCount){
                units.add(unitType.spawn(team, x, y));
                consume();
            }
        }
    }
}
