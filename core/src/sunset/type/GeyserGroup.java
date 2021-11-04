package sunset.type;

import arc.func.Floatf;
import arc.func.Func;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.Effect;
import mindustry.world.Tile;
import sunset.world.GeyserLogic;
import sunset.world.blocks.environment.Geyser;

public class GeyserGroup {
    public Seq<Tile> tiles;
    private GeyserState state;
    private float factor;

    public GeyserGroup(Seq<Tile> tiles) {
        this.tiles = tiles;
    }

    public float factor() {
        return factor;
    }

    public float calculateFactor() {
        return factor = 1f / Mathf.pow(tiles.size, 0.333f);
    }

    public void upState() {
        state=state.up();
    }
    public void downState() {
        state=state.down();
    }

    public void effect() {
        Geyser geyser = (Geyser) tiles.get(0).floor();
        Effect e = state.effect(geyser);
        float d = state.damage(geyser);
        tiles.each(pos -> {
            e.at(pos);
            if (d > 0 && pos.build != null) pos.build.damage(d * 30);
        });
    }
    enum GeyserState {
        ready(g -> g.calmEffect, g -> 0f),
        steady(g -> g.steamEffect, g -> g.steamDamage),
        go(g -> g.eruptionEffect, g -> g.eruptionDamage);
        final Func<Geyser, Effect> effect;
        final Floatf<Geyser> damage;

        GeyserState(Func<Geyser, Effect> effect, Floatf<Geyser> damage) {
            this.effect = effect;
            this.damage = damage;
        }

        public GeyserState next() {
            return values()[(ordinal() + 1) % values().length];
        }

        public GeyserState up() {
            return values()[Math.min(ordinal() + 1, values().length)];
        }

        public GeyserState down() {
            return values()[Math.min(ordinal() - 1, 0)];
        }

        public Effect effect(Geyser geyser) {
            return effect.get(geyser);
        }

        public float damage(Geyser geyser) {
            return damage.get(geyser);
        }
    }
}
