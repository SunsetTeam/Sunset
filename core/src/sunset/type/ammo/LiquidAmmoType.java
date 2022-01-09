package sunset.type.ammo;

import arc.graphics.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import sunset.content.*;

public class LiquidAmmoType implements AmmoType{
    public float range = 85f;
    public int ammoPerLiquid = 1;
    public Liquid liquid;
    public Color barColor = new Color(Pal.ammo);

    public LiquidAmmoType(Liquid liquid){
        this.liquid = liquid;
    }

    public LiquidAmmoType(Liquid liquid, int ammoPerLiquid){
        this.liquid = liquid;
        this.ammoPerLiquid = ammoPerLiquid;
    }

    public LiquidAmmoType(){
    }

    @Override
    public String icon(){
        return liquid.emoji();
    }

    @Override
    public Color color(){
        return liquid.color;
    }

    @Override
    public Color barColor(){
        return barColor;
    }

    @Override
    public void resupply(Unit unit){
        //do not resupply when it would waste resources
        if(unit.type.ammoCapacity - unit.ammo < ammoPerLiquid) return;

        float range = unit.hitSize + this.range;

        Building build = Units.closestBuilding(unit.team, unit.x, unit.y, range, u -> u.canResupply() && u.block.hasLiquids && u.liquids.get(liquid) > 0.0001f);

        if(build != null){
            SnFx.liquidTransfer.at(build.x, build.y, ammoPerLiquid / 2f, liquid.color, unit);
            unit.ammo = Math.min(unit.ammo + ammoPerLiquid, unit.type.ammoCapacity);
            build.liquids.remove(liquid, 1);
        }
    }
}
