package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Player;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import sunset.gen.*;
import sunset.world.MissileLogic;
import sunset.world.meta.values.MinMaxRangeValues;
import sunset.world.meta.values.SplashDamageValue;

import static mindustry.Vars.tilesize;
import static mindustry.game.EventType.*;

public class MissileSiloTurret extends GenericCrafter {
    public MissileLogic.MissileType missile;
    public Effect launchEffect = Fx.none;
    public float maxRange;
    public float minRange;
    /** Положение ракет в шахте относительно размеров блока. От (0, 0) до (1, 1). */
    public Seq<Vec2> rockets = new Seq<>(new Vec2[]{new Vec2(0.5f, 0.5f)});
    public TextureRegion baseRegion;

    public MissileSiloTurret(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        SnContentRegions.loadRegions(this);
        baseRegion = Core.atlas.find(name + "-bottom");
        missile.rocketRegion = Core.atlas.find(name + "-rocket");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{baseRegion, region};
    }
    @Override
    public void createIcons(MultiPacker packer){
        super.createIcons(packer);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.damage, new SplashDamageValue(missile.damage,
                missile.splashDamage,
                missile.splashDamageRadius));
        stats.add(Stat.range, new MinMaxRangeValues(minRange, maxRange));
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("progress", (MissileSiloTurretBuild e) -> new Bar("bar.progress",
                Pal.ammo,
                () -> e.loaded == rockets.size ? 1f : e.progress));
        bars.add("count", (MissileSiloTurretBuild e) -> new Bar(
                () -> Core.bundle.format("bar.missilecount", e.loaded, rockets.size),
                () -> Pal.ammo,
                () -> e.loaded / (float)rockets.size));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, minRange, Pal.placing);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, maxRange, Pal.placing);
    }

    public static MissileSiloTurretBuild selected = null;
    static {
        Events.on(WorldLoadEvent.class, (e) -> selected = null);
        Events.on(GameOverEvent.class, (e) -> selected = null);
    }

    public class MissileSiloTurretBuild extends GenericCrafterBuild {
        /** Количество загруженных ракет. Максимальный размер определяется rockets.size */
        public int loaded = 0;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation) {
            Events.on(TapEvent.class, this::tapTile);
            Events.on(Trigger.uiDrawEnd.getClass(), this::drawSelectIf);
            return super.init(tile, team, shouldAdd, rotation);
        }

        private void drawSelectIf(Trigger t) {
            if (selected == this) {
                Draw.z(Layer.overlayUI);
                drawSelect();
            }
        }

        @Override
        public int getMaximumAccepted(Item item){
            int res = 0;
            for (ItemStack i : consumes.getItem().items) {
                if(i.item == item) res += i.amount;
            }
            return res*(rockets.size+1);
        }

        @Override
        public void tapped() {
            if(selected == this) {
                selected = null;
            } else {
                selected = this;
            }
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            Events.remove(TapEvent.class, this::tapTile);
            Events.remove(Trigger.uiDrawEnd.getClass(), this::drawSelectIf);
            if(selected == this) selected = null;
        }

        private void tapTile(TapEvent te) {
            Player p = te.player;
            if(p.team() != team) return;
            if(loaded == 0 || selected != this) return;
            Vec2 v = Core.input.mouseWorld();
            float dst = v.dst(x, y);
            if(dst > maxRange || dst < minRange) return;
            Vec2 from = new Vec2(x + size * tilesize * (rockets.get(loaded - 1).x - 0.5f),
                    y + size * tilesize * (rockets.get(loaded - 1).y - 0.5f));
            launchEffect.at(from);
            missile.launch(from, v);
            loaded--;
        }

        @Override
        public void updateTile() {
            if(consValid() && loaded < rockets.size){
                progress += getProgressIncrease(craftTime);
                totalProgress += delta();
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);
                if(Mathf.chanceDelta(updateEffectChance)) {
                    updateEffect.at(getX() + Mathf.range(size * 4f), getY() + Mathf.range(size * 4));
                }
            } else {
                warmup = Mathf.lerp(warmup, 0f, 0.02f);
            }
            if(progress >= 1f) {
                consume();
                loaded++;
                craftEffect.at(x, y);
                progress %= 1f;
            }
        }

        @Override
        public void draw() {
            Draw.z(Layer.block);
            Draw.rect(baseRegion, x, y);
            for(int i = 0; i < loaded; i++) {
                Draw.rect(missile.rocketRegion,
                        x + size * tilesize * (rockets.get(i).x - 0.5f),
                        y + size * tilesize * (rockets.get(i).y - 0.5f));
                // ... / 64f * tilesize = / 2f * tilesize / 32f
                // / 2f  - нужна только половина блока
                // / 32f - 32 пикселя в спрайте на 1 блок
                // * tilesize - перевод координат
            }
            Draw.rect(region, x, y);
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, minRange, team.color);
            Drawf.dashCircle(x, y, maxRange, team.color);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(loaded);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            loaded = read.i();
        }
    }
}