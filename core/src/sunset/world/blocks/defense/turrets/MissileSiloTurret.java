package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations.Load;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.*;
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
import sunset.type.MissileType;
import sunset.world.meta.values.MinMaxRangeValues;
import sunset.world.meta.values.SplashDamageValue;

import static mindustry.Vars.reportIssueURL;
import static mindustry.Vars.tilesize;
import static mindustry.game.EventType.*;

public class MissileSiloTurret extends GenericCrafter {
    public static MissileSiloTurretBuild selected = null;

    static {
        Events.on(WorldLoadEvent.class, (e) -> selected = null);
        Events.on(GameOverEvent.class, (e) -> selected = null);
        Events.run(Trigger.draw, () -> {
            if (selected != null) {
                selected.drawSelectIf();
            }
//            Player player = selected.unit().getPlayer();
            if (Vars.player.unit() instanceof BlockUnitc b && b.tile() instanceof MissileSiloTurretBuild build) {
                build.drawSelectIf();
            }
            /*if (player != null && player == Vars.player) {
                selected.drawSelectIf();
            }*/
        });
        Events.on(TapEvent.class, e -> {
            if (selected != null && e.player == Vars.player) {
                selected.tapTile(e.player, e.tile);
            } else if (e.player != null && e.player.unit() instanceof BlockUnitc b && b.tile() instanceof MissileSiloTurretBuild build) {
                build.tapTile(e.player, e.tile);
            }
        });
    }

    public MissileType missile;
    public Effect launchEffect = Fx.none;
    public float maxRange;
    public float minRange;
    /**
     * Положение ракет в шахте относительно размеров блока. От (0, 0) до (1, 1).
     */
    public Vec2[] rockets = {new Vec2(0.5f, 0.5f)};
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
        this.rockets = rockets;
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{baseRegion, region};
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

    private Vec2 calculatePosition(Vec2 rocket) {
        return Tmp.v1.set(rocket).sub(0.5f, 0.5f).scl(tilesize * size);
    }

    public class MissileSiloTurretBuild extends GenericCrafterBuild implements ControlBlock {
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

        private void drawSelectIf() {
            Draw.draw(Layer.overlayUI, () -> {
                drawSelect();
            });
        }

        @Override
        public int getMaximumAccepted(Item item) {
            int res = 0;
            for (ItemStack i : consumes.getItem().items) {
                if (i.item == item) res += i.amount;
            }
            return res * (rockets.length + 1);
        }

        @Override
        public void tapped() {
            if (true) return;
            if (selected == this) {
                selected = null;
            } else {
                selected = this;
            }
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
//            Events.remove(TapEvent.class, this::tapTile);
//            Events.remove(Trigger.uiDrawEnd.getClass(), this::drawSelectIf);
            if (selected == this) selected = null;
        }

        private void tapTile(Player player, Tile tile) {
            if (player.team() != team || Vars.player != player) return;
            if (isControlled()) {
                tryShoot(unit.aimX(), unit.aimY());
            } else {
                tryShoot(tile.worldx(), tile.worldy());
            }
        }

        private void tryShoot(float shootX, float shootY) {
            if (loaded == 0) {
                return;
            }
            float dst = dst(shootX, shootY);
            if (dst > maxRange || dst < minRange) return;
            Vec2 from = new Vec2(x + size * tilesize * (rockets[loaded - 1].x - 0.5f),
                    y + size * tilesize * (rockets[loaded - 1].y - 0.5f));
            launchEffect.at(from);
            missile.launch(from, new Vec2(shootX, shootY));
            loaded--;
            if (loaded == 0 && selected == this) {

//                tapped();
            }
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

            for (int i = 0; i < loaded; i++) {
                Vec2 rocket = calculatePosition(rockets[i]);
                Draw.rect(missile.rocketRegion,
                        x + rocket.x,
                        y + rocket.y);
                // ... / 64f * tilesize = / 2f * tilesize / 32f
                // / 2f  - нужна только половина блока
                // / 32f - 32 пикселя в спрайте на 1 блок
                // * tilesize - перевод координат
            }

            if (loaded < rockets.length) {
                Vec2 rocketPosition = calculatePosition(rockets[loaded]).add(this);
                float rocketx=rocketPosition.x;
                float rockety=rocketPosition.y;
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