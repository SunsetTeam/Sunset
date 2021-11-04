package sunset.world;

import arc.Events;
import arc.math.Mathf;
import arc.struct.FloatSeq;
import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.util.Interval;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.world.Tile;
import sunset.world.blocks.environment.Geyser;

import static mindustry.Vars.world;

public class GeyserLogic {
    public static void init(){
        Events.on(EventType.WorldLoadEvent.class, (e) -> reloadData());
        Events.on(EventType.Trigger.update.getClass(), (t) -> update());
    }
    private static final Seq<Seq<Tile>> geysers = new Seq<>(), parts = new Seq<>();
    private static final IntSeq states = new IntSeq();
    private static final FloatSeq factors = new FloatSeq();
    private static float sumfactors;
    private static void reloadData() {
        //TODO оптимизировать???
        geysers.clear();
        world.tiles.eachTile(tile -> {
            if (!(tile.floor() instanceof Geyser)) return;
            geysers.each(tiles -> {
                if(tiles.contains(pred-> pred.nearby(0) == tile ||
                        pred.nearby(1) == tile ||
                        pred.nearby(2) == tile ||
                        pred.nearby(3) == tile)) {
                    parts.add(tiles);
                }
            });
            if(parts.isEmpty()) {
                geysers.add(Seq.with(tile));
            } else {
                parts.first().add(tile);
                if(parts.size > 1) {
                    for(int i = parts.size-1; i > 0; i--) {
                        parts.first().addAll(parts.get(i));
                        geysers.remove(parts.get(i));
                    }
                }
            }
            parts.clear();
        });
        states.clear();
        states.setSize(geysers.size);
        //update factors
        sumfactors = 0;
        factors.clear();
        for(int i = 0; i < geysers.size; i++) {
            float k = 1f / Mathf.pow(geysers.get(i).size, 0.333f);
            sumfactors += k;
            factors.add(k);
        }
    }
    private static float baseUpChance = 0.00015f;
    private static float baseDownChance = 0.0001f;
    private static Interval effects = new Interval();
    private static void update() {
        if(geysers.isEmpty()) return;
        //update states
        //0 - спокоен, 1 - пар, 2 - извергается
        if(Mathf.chance(baseUpChance)) {
            int i = randomGeyser();
            int v = states.get(i);
            if(v < 2) states.set(i, v+1);
        } else if(Mathf.chance(baseDownChance)) {
            int i = randomGeyser();
            int v = states.get(i);
            if(v > -1) states.set(i, v-1);
        }
        //apply effects
        if(effects.get(30)) {
            for(int i = 0; i < geysers.size; i++) {
                int s = states.get(i);
                Seq<Tile> tiles = geysers.get(i);
                Geyser g = (Geyser)tiles.get(0).floor();
                Effect e = s == 0 ? g.calmEffect :
                           s == 1 ? g.steamEffect :
                                    g.eruptionEffect;
                float d = s == 0 ? 0f :
                          s == 1 ? g.steamDamage :
                                   g.eruptionDamage;
                tiles.each(pos -> {
                    e.at(pos);
                    if(d > 0 && pos.build != null) pos.build.damage(d * 30);
                });
            }
        }
    }
    private static int randomGeyser(){
        float f = Mathf.random(sumfactors);
        int r = 0;
        while (r < factors.size && f > factors.get(r)) {
            f -= factors.get(r);
            r++;
        }
        return r;
    }
}
