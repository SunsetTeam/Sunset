package sunset.world.blocks.production.raw;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
;
import sunset.type.blocks.*;
import sunset.world.consumers.*;

import java.util.*;

import static mindustry.Vars.*;

public class PrecussionDrill extends Block{
    private static final ItemSeq tmpItems = new ItemSeq();
    /**
     * The higher, the faster it drills. Stronger effect on soft ores than on hard ores.
     */
    public float hardnessDrillMultiplier = 6f;
    /**
     * The higher, the faster it drills. Same effect on all ores.
     */
    public float itemCountMultiplier = 1f;
    /**
     * Во сколько раз ускоряется жидкостью.
     */
    public float liquidBoost = 1.6f;
    /**
     * The duration of the extraction of one batch of resources.
     */
    public float drillTime = 540f;
    /**
     * List of resources used in mining.
     */
    public DrillItem[] reqDrillItems = {};
    /**
     * Capacity of consumed resources
     */
    public int drillItemCapacity = 10;

    /**
     * automatic unloading on adjacent blocks
     */
    public boolean canDump = false;

    public Effect downEffect = Fx.unitLand;
    public int tier = 5;
    public float powerUse = 1f;
    public Color heatColor = Color.valueOf("ff5512");
    //@Load("@-rim")
    public TextureRegion rimRegion;
    //@Load("@-rotator")
    public TextureRegion rotatorRegion;
    //@Load("@-top")
    public TextureRegion topRegion;

    public float shakeIntensity = 3f;
    public float shakeDuration = 8f;
    public Sound drillSound = Sounds.none;
    public float soundPitch = 1f;
    public float soundVolume = 1f;
    public Interp scaleInterp = p -> {
        float toa = 1.07f;
        float progress = p * toa;
        if(progress > 1f){
            return Mathf.map(progress, 1f, toa, 1.3f, 0.9f);
        }else{
            return Mathf.map(progress, 0, 1, 0.9f, 1.3f);
        }
    };


    public PrecussionDrill(String name){
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.drills;
        hasLiquids = true;
        liquidCapacity = 5f;
        hasItems = true;
        ambientSound = Sounds.drill;
        ambientSoundVolume = 0.018f;
    }

    public static State updateOre(Tile tile, ItemSeq items, int tier, PrecussionDrill inst){
        State ret = State.noOre;
        items.clear();
        for(Tile t : tile.getLinkedTilesAs(inst, tempTiles)){
            if(t != null && t.drop() != null){
                if(t.drop().hardness <= tier){
                    items.add(t.drop());
                    ret = State.ok;
                }else if(ret == State.noOre) ret = State.lowTier;
            }
        }
        return ret;
    }

    public State updateOre(Tile tile, ItemSeq items, int tier){
        return updateOre(tile, items, tier, this);
    }

    public void drillItems(DrillItem... drillItems){
        this.reqDrillItems = drillItems;
    }

    @Override
    public TextureRegion[] icons(){
        return  new TextureRegion[]{region, rotatorRegion, topRegion};
    }

    @Override
    public void init(){
        for(DrillItem drillItem : reqDrillItems){
            if(drillItem.amount == -1){
                drillItem.amount = 2;
            }
        }
        consumePowerDynamic((PrecussionDrillBuild p) -> {
            return powerUse * (p.working() ? p.timeScale() : 0);
        });
//        consumes.add(new ConsumeItemFilter(i -> Structs.contains(reqDrillItems, d -> d.item == i)));
        Arrays.sort(reqDrillItems, Structs.comparingFloat(di -> di.sizeMultiplier));
        consume(new DrillItemsConsume(reqDrillItems));
        super.init();

    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(Stat.itemCapacity); //dynamic capacity and equal to the batch size
        stats.add(Stat.productionTime, drillTime / 60f, StatUnit.seconds);
        stats.remove(Stat.powerUse);
        stats.add(Stat.powerUse, powerUse * 60f, StatUnit.powerSecond);
        stats.add(Stat.drillTier, StatValues.blocks(b -> b instanceof Floor && b.itemDrop != null && b.itemDrop.hardness <= tier));
        stats.add(Stat.drillSpeed, (hardnessDrillMultiplier * size * size * itemCountMultiplier) / (drillTime / 60f), StatUnit.itemsSecond);
        if(liquidBoost != 1){
            stats.add(Stat.boostEffect, liquidBoost, StatUnit.timesSpeed);
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("progress", (PrecussionDrillBuild e) ->
        new Bar(() -> Core.bundle.get("bar.drillprogress"),
        () -> Pal.surge,
        () -> e.progressTime / drillTime));
        addBar("drillspeed", (PrecussionDrillBuild e) ->
        new Bar(() -> Core.bundle.format("bar.drillspeed",
        Strings.fixed(e.displaySpeed, 2)),
        () -> Pal.ammo,
        () -> e.currentSpeed));
    }

    public boolean canPlaceOn(Tile tile, Team team){
        return updateOre(tile, tmpItems, tier) == State.ok;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Tile tile = world.tile(x, y);
        if(tile == null) return;

        State state = updateOre(tile, tmpItems, tier);

        if(state == State.ok){
            final float[] sumSpeed = {0};
            tmpItems.each((item, amount) -> sumSpeed[0] += (multiplier(item) * amount) / (drillTime / 60f));
            float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", sumSpeed[0] * itemCountMultiplier, 2), x, y, valid);
            final float[] dx = {x * tilesize + offset - width / 2f - 4f};
            float dy = y * tilesize + offset + size * tilesize / 2f + 5;
            tmpItems.each((item, amount) -> {
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.uiIcon, dx[0], dy - 1);
                Draw.reset();
                Draw.rect(item.uiIcon, dx[0], dy);
                dx[0] -= 6;
            });
        }else if(state == State.lowTier){
            Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> t.drop() != null && t.drop().hardness > tier);
            Item item = to == null ? null : to.drop();
            if(item != null) drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, valid);
        }
    }

    public float multiplier(Item item){
        return hardnessDrillMultiplier / Mathf.sqrt(item.hardness + 1f);

    }

    private enum State{
        noOre, //no ore under the drill
        lowTier, //"Better drill required"
        ok //everything is good
    }

    public class PrecussionDrillBuild extends Building{
        final ItemSeq drillItems = new ItemSeq(); //list of ore under the drill
        public float displaySpeed, baseDisplaySpeed;
        public float currentSpeed, totalSpeed;
        public float progressTime;
        public float totalBoost;
        public float warmupSpeed = 0.02f;
        private int offloadSize; //batch size = number of items given out at a time
        private DrillItem currentDrillItem; //current item to drill

        public float baseSpeed(){
            return working() ? (power == null) ? 1f : power.status : 0f;
        }

        @Override
        public void created(){
            updateOre(tile, drillItems, tier);
            drillItems.each((item, amount) -> {
                baseDisplaySpeed += (multiplier(item) * amount) / (drillTime / 60f);
                offloadSize += (int)(amount * multiplier(item));
            });
            baseDisplaySpeed *= itemCountMultiplier;
        }

        @Override
        public void update(){
            super.update();
        }

        @Override
        public void updateTile(){
            currentDrillItem = Structs.find(reqDrillItems, di -> items.has(di.item, di.amount));

            currentSpeed = Mathf.lerpDelta(currentSpeed, baseSpeed(), warmupSpeed);
            if(!working()){
                return;
            }
            //speed count
            totalBoost = 1f;
            if(optionalEfficiency > 0) totalBoost *= liquidBoost;
            totalSpeed = currentSpeed * totalBoost;
            //update display value
            displaySpeed = baseDisplaySpeed * totalSpeed;
            if(currentDrillItem != null) displaySpeed *= currentDrillItem.sizeMultiplier;
            //updating
            progressTime += delta() * totalSpeed;
            if(progressTime >= drillTime && working()){
                consume();
                Effect.shake(shakeIntensity, shakeDuration, x, y);
                drillSound.at(x, y, soundPitch, soundVolume);

                progressTime %= drillTime;
                downEffect.at(this);
                drillItems.each((item, amount) -> {
                    float multiplier = multiplier(item) * currentDrillItem.sizeMultiplier;
                    for(int i = 0; i < (int)(amount * multiplier * itemCountMultiplier); i++){
                        offload(item);
                    }
                });
            }
            if(canDump){
                items.each((i, a) -> {
                    if(Structs.contains(reqDrillItems, s -> s.item == i)) return;
                    dump(i);
                });
//                drillItems.each((i, a) -> dump(i));
            }
        }
        // the possibility of multi-mining breaks the usual withdrawal of resources,
        // for stable operation, you need to use an unloader

        @Override
        public boolean acceptItem(Building source, Item item){
            boolean accept = currentDrillItem == null || currentDrillItem.item == item;
            return accept && Structs.contains(reqDrillItems, di -> di.item == item) && items().get(item) < drillItemCapacity;
        }

        @Override
        public boolean canDump(Building to, Item item){
            return canDump && drillItems.has(item);
        }

        @Override
        public int getMaximumAccepted(Item item){
            if(Structs.contains(reqDrillItems, di -> di.item == item)) return drillItemCapacity;
            return 0;
        }

        private boolean working(){
            return enabled && (items().total() < offloadSize) && (currentDrillItem != null) && efficiency>0;
        }

        @Override
        public void drawSelect(){
            final float[] dx = {x - size * tilesize / 2f};
            float dy = y + size * tilesize / 2f;
            drillItems.each((item, amount) -> {
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.uiIcon, dx[0], dy - 1);
                Draw.reset();
                Draw.rect(item.uiIcon, dx[0], dy);
                dx[0] -= 6;
            });
        }

        @Override
        public void draw(){

            Draw.rect(region, x, y);
            super.drawCracks();

            Draw.color(heatColor);
            //Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
            Draw.blend(Blending.additive);
            Draw.rect(rimRegion, x, y);
            Draw.blend();
            Draw.color();
            float xscl = Draw.xscl, yscl = Draw.yscl;
            Draw.scl(scaleInterp.apply(progress()));
            Drawf.spinSprite(rotatorRegion, x, y, 2);

            Draw.rect(topRegion, x, y);
            Draw.scl(xscl, yscl);
        }

        public void effect(){
            if(drillTime > 0){
                Effect.shake(drillTime, drillTime, this);
            }
        }

        public float progress(){
            return progressTime / drillTime;
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progressTime);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision == 0){
                return;
            }
            progressTime = read.f();
        }
    }
}
