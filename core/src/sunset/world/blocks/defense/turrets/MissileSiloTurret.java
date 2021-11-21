package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Nullable;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations.Load;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.entities.Effect;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Building;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.MultiPacker;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import mma.ModVars;
import mma.graphics.ModFill;
import sunset.type.MissileType;
import sunset.world.meta.values.MinMaxRangeValues;
import sunset.world.meta.values.SplashDamageValue;

import static mindustry.Vars.tilesize;
import static mindustry.game.EventType.TapEvent;
import static mindustry.game.EventType.Trigger;

public class MissileSiloTurret extends GenericCrafter {

    static {
    }

    public MissileType missile;
    public Effect launchEffect = Fx.none;
    public float maxRange;
    public float minRange;
    /**
     * Положение ракет в шахте относительно размеров блока. От (0, 0) до (1, 1).
     */
    public MissilePlace[] rockets = {new MissilePlace(0.5f, 0.5f)};
    @Load("@-bottom")
    public TextureRegion baseRegion;

    public MissileSiloTurret(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        missile.load();
    }

    public void rockets(Vec2... rockets) {
        this.rockets = new MissilePlace[rockets.length];
        for (int i = 0; i < this.rockets.length; i++) {
            this.rockets[i] = new MissilePlace(rockets[i]);
        }
    }

    @Override
    public TextureRegion[] icons() {
        return !ModVars.packSprites ? new TextureRegion[]{region} : new TextureRegion[]{baseRegion, region};
    }


    @Override
    public void createIcons(MultiPacker packer) {
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
                () -> e.loaded == rockets.length ? 1f : e.progress));
        bars.add("count", (MissileSiloTurretBuild e) -> new Bar(
                () -> Core.bundle.format("bar.missilecount", e.loaded, rockets.length),
                () -> Pal.ammo,
                () -> e.loaded / (float) rockets.length));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, minRange, Pal.placing);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, maxRange, Pal.placing);
    }

    private Vec2 calculatePosition(MissilePlace rocket) {
        return Tmp.v1.set(rocket).sub(0.5f, 0.5f).scl(tilesize * size);
    }

    public static class MissilePlace implements Position {
        public float x, y;

        public MissilePlace(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public MissilePlace(Position position) {
            this(position.getX(), position.getY());
        }

        public Vec2 positionAt(Building build, Vec2 reference) {
            return reference.set(this).sub(0.5f, 0.5f).scl(build.block.size * tilesize).add(build);
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }
    }

    public class MissileSiloTurretBuild extends GenericCrafterBuild implements ControlBlock, MissileSileBuild {
        /**
         * Количество загруженных ракет. Максимальный размер определяется rockets.size
         */
        public int loaded = 0;
        public @Nullable
        BlockUnitc unit;

        @Override
        public Unit unit() {
            if (unit == null) {
                unit = (BlockUnitc) UnitTypes.block.create(team);
                unit.tile(this);
            }
            return (Unit) unit;
        }

        @Override
        public boolean canControl() {
            return true;
        }

        @Override
        public boolean shouldAutoTarget() {
            return false;
        }
        @Override
        public void drawControlled() {
            Draw.draw(Layer.overlayUI, () -> {
                drawSelect();
            });
        }

        @Override
        public void drawActiveTerritory() {
            ModFill.doubleSwirl(x,y,minRange,maxRange,1f,0);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            int res = 0;
            for (ItemStack i : consumes.getItem().items) {
                if (i.item == item) res += i.amount;
            }
            return res * (rockets.length + 1);
        }

        public void tapTile(Player player, Tile tile) {
            if (player.team() != team || Vars.player != player) return;
            if (isControlled()) {
                tryShoot(unit.aimX(), unit.aimY());
            } else {
                tryShoot(tile.worldx(), tile.worldy());
            }
        }

        @Override
        public boolean tryShoot(float shootX, float shootY) {
            if (loaded == 0) {
                return false;
            }
            float dst = dst(shootX, shootY);
            if (dst > maxRange || dst < minRange) return false;
            Vec2 from = rockets[loaded - 1].positionAt(this, new Vec2());
            launchEffect.at(from);
            missile.launch(from, new Vec2(shootX, shootY));
            loaded--;
            return true;
        }

        public void updateShooting() {
            if (unit == null || !isControlled()) return;

            unit.health(health);
            float ammof = loaded / (float) rockets.length;
            if (loaded != rockets.length) {
                ammof += progress / rockets.length;
            }

            unit.ammo(unit.type().ammoCapacity * ammof);
            unit.team(team);
            unit.set(x, y);
            if (!unit.isShooting()) {
                return;
            }
        }

        @Override
        public void updateTile() {
            updateShooting();
            if (consValid() && loaded < rockets.length) {
                progress += getProgressIncrease(craftTime);
                totalProgress += delta();
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);
                if (Mathf.chanceDelta(updateEffectChance)) {
                    updateEffect.at(getX() + Mathf.range(size * 4f), getY() + Mathf.range(size * 4));
                }
            } else {
                warmup = Mathf.lerp(warmup, 0f, 0.02f);
            }
            if (progress >= 1f) {
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
            Draw.rect(region, x, y);

            Vec2 rocket = Tmp.v1;
            for (int i = 0; i < loaded; i++) {
                rockets[i].positionAt(this, rocket);
                Draw.rect(missile.rocketRegion, rocket.x, rocket.y);
            }

            if (loaded < rockets.length) {
                float rocketx = rockets[loaded].positionAt(this, Tmp.v1).x;
                float rockety = Tmp.v1.y;
                Draw.draw(Layer.blockOver, () -> Drawf.construct(rocketx, rockety, missile.rocketRegion,
                        0, progress, warmup, totalProgress));
            }
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, minRange, team.color);
            Drawf.dashCircle(x, y, maxRange, team.color);
        }


        @Override
        public byte version() {
            return 0;
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