package sunset.world.blocks.defense.turrets;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.production.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
;
;
import sunset.type.*;
import sunset.utils.*;
import sunset.world.meta.*;

import static mindustry.Vars.tilesize;

public class MissileSiloTurret extends GenericCrafter {

    static {
    }

    public MissileType missile;
    public Effect launchEffect = Fx.none;
    public float maxRange;
    public float minRange;
    /**
     * The position of the missiles in the silo relative to the block size. From (0, 0) to (1, 1).
     */
    public MissilePlace[] rockets = {new MissilePlace(0.5f, 0.5f)};
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
        return new TextureRegion[]{baseRegion, region};
    }


    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.damage, SnStatValues.splashDamage(missile.damage, missile.splashDamage, missile.splashDamageRadius));
        stats.add(Stat.range, SnStatValues.minMaxRange(minRange, maxRange));
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("progress", (MissileSiloTurretBuild e) -> new Bar("bar.progress",
                Pal.ammo,
                () -> e.loaded == rockets.length ? 1f : e.progress));
        addBar("count", (MissileSiloTurretBuild e) -> new Bar(
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
         * The number of missiles loaded. The maximum size is determined by rockets.size
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
        }

        @Override
        public int getMaximumAccepted(Item item) {
            int res = 0;
            for(Consume consumer : block.consumers){
                if(consumer instanceof ConsumeItems items){
                    for(ItemStack i : items.items){
                        if(i.item == item) res += i.amount;
                    }
                }
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
            if (efficiency>0 && loaded < rockets.length) {
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