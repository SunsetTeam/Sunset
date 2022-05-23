package sunset.world.blocks.defense.turrets;

import acontent.world.meta.AStats;
import arc.Core;
import arc.func.Boolf;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.gl.FrameBuffer;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.struct.IntSeq;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.UnitTypes;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Building;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.ControlBlock;
import sunset.world.meta.SnStat;

import static mindustry.Vars.*;

public class MissileSiloCommander extends Block {
    public float commandRange = 8;
    public float maxConnections = 10;
    public boolean canConnectCommanders = false;
    public Boolf<Building> filter = (b) -> canConnectCommanders || !(b.block instanceof MissileSiloCommander);
    public AStats aStats = new AStats();

    public MissileSiloCommander(String name) {
        super(name);
        stats = aStats.copy(stats);
        update = true;
        noUpdateDisabled = false;
        configurable = true;
        config(Integer.class, (MissileSiloCommanderBuild build, Integer pos) -> {
            if (build.missilePositions.contains(pos)) {
                build.missilePositions.removeValue(pos);
            } else{
                build.missilePositions.add(pos);
//            build.addTurret(pos)
            };
        });

        config(Point2[].class, (MissileSiloCommanderBuild tile, Point2[] value) -> {
            //set new
            for(Point2 p : value){
                int newPos = Point2.pack(p.x + tile.tileX(), p.y + tile.tileY());
                configurations.get(Integer.class).get(tile, newPos);
            }
        });
    }

    @Override
    public void setStats() {
        super.setStats();
        aStats.add(SnStat.maxControlled, maxConnections);
    }

    @Override
    public void setBars() {
        super.setBars();
        this.<MissileSiloCommanderBuild>addBar("connections", entity -> new Bar(() ->
                Core.bundle.format("bar.connections", entity.missilePositions.size, maxConnections),
                () -> Pal.items,
                () -> (float)entity.missilePositions.size / (float)maxConnections
        ));
    }

    public boolean linkValid(Building tile, Building link) {
        return linkValid(tile, link, true);
    }

    public boolean linkValid(Building tile, Building link, boolean checkMaxNodes) {
        if (tile == link || !(link instanceof MissileSileBuild) || !filter.get(link) || tile.team != link.team || tile instanceof MissileSiloCommanderBuild c && c.linked(tile))
            return false;

        if (overlaps(tile, link, commandRange * tilesize) || (link.block instanceof MissileSiloCommander commander && canConnectCommanders && overlaps(link, tile, commander.commandRange * tilesize))) {
            if (checkMaxNodes && link.block instanceof MissileSiloCommander commander && canConnectCommanders) {
                return as(link).missilePositions.size < commander.maxConnections || as(link).missilePositions.contains(tile.pos());
            } else if (checkMaxNodes && !(link.block instanceof MissileSiloCommander)) {
                return as(tile).missilePositions.size < maxConnections || as(tile).missilePositions.contains(link.pos());
            }
            return true;
        }
        return false;
    }

    private MissileSiloCommanderBuild as(Building building) {
        return (MissileSiloCommanderBuild) building;
    }

    protected boolean overlaps(float srcx, float srcy, Tile other, Block otherBlock, float range) {
        return Intersector.overlaps(Tmp.cr1.set(srcx, srcy, range), Tmp.r1.setCentered(other.worldx() + otherBlock.offset, other.worldy() + otherBlock.offset,
                otherBlock.size * tilesize, otherBlock.size * tilesize));
    }

    protected boolean overlaps(float srcx, float srcy, Tile other, float range) {
        return Intersector.overlaps(Tmp.cr1.set(srcx, srcy, range), other.getHitbox(Tmp.r1));
    }

    protected boolean overlaps(Building src, Building other, float range) {
        return overlaps(src.x, src.y, other.tile(), range);
    }

    protected boolean overlaps(Tile src, Tile other, float range) {
        return overlaps(src.drawx(), src.drawy(), other, range);
    }

    public boolean overlaps(@Nullable Tile src, @Nullable Tile other) {
        if (src == null || other == null) return true;
        return Intersector.overlaps(Tmp.cr1.set(src.worldx() + offset, src.worldy() + offset, commandRange * tilesize), Tmp.r1.setSize(size * tilesize).setCenter(other.worldx() + offset, other.worldy() + offset));
    }

    public class MissileSiloCommanderBuild extends Building implements ControlBlock, MissileSileBuild {
        //        public Seq<MissileSiloTurret.MissileSiloTurretBuild> turretBuilds = new Seq<>();
        public IntSeq missilePositions = new IntSeq();
        public @Nullable
        BlockUnitc unit;
        private Runnable afterRead = null;

        @Override
        public Point2[] config(){
            Point2[] out = new Point2[missilePositions.size];
            for(int i = 0; i < out.length; i++){
                out[i] = Point2.unpack(missilePositions.get(i)).sub(tile.x, tile.y);
            }
            return out;
        }
        @Override
        public void update() {
            if (afterRead != null) {
                afterRead.run();
                afterRead = null;
            }
            for (int i = 0; i < missilePositions.size; i++) {
                int position = missilePositions.get(i);
                if (missilePositions.size>maxConnections || !linkValid(this,world.build(position))){
//                    missilePositions.removeIndex(i);
                    configure(position);
                    i--;
                }
            }
            super.update();
        }


        @Override
        public Unit unit() {
            if (unit == null) {
                unit = (BlockUnitc) UnitTypes.block.create(team);
                unit.tile(this);
            }
            return (Unit) unit;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (linkValid(this, other)) {
                configure(other.pos());
                return false;
            }

            if (this == other) {
                while (missilePositions.size > 0) {
                    configure(missilePositions.first());
                }
                deselect();
                return false;
            }

            return true;
        }

        @Override
        public void drawSelect() {
            super.drawSelect();

            Lines.stroke(1f);

            Draw.color(Pal.accent);
            Drawf.circles(x, y, commandRange * tilesize);
            Draw.reset();
        }

        @Override
        public void drawConfigure() {

            Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f));
            Drawf.circles(x, y, commandRange * tilesize);

            for (int x = (int) (tile.x - commandRange - 2); x <= tile.x + commandRange + 2; x++) {
                for (int y = (int) (tile.y - commandRange - 2); y <= tile.y + commandRange + 2; y++) {
                    Building link = world.build(x, y);

                    if (link != this && linkValid(this, link, false)) {
                        boolean linked = linked(link);

                        if (linked) {
                            Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f, Pal.place);
                        }
                    }
                }
            }

            Draw.reset();
        }

        public void addTurret(int pos) {
            missilePositions.add(pos);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(missilePositions.size);
            missilePositions.each(write::i);
        }

        protected boolean linked(Building other) {
            return missilePositions.contains(other.pos());
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision == 0) {
                return;
            }
            int size = read.i();
            missilePositions.clear();
            for (int i = 0; i < size; i++) {
                missilePositions.add(read.i());
            }
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public boolean tryShoot(float shootX, float shootY) {
            for (int i = 0; i < missilePositions.size; i++) {
                if (world.build(missilePositions.get(i)) instanceof MissileSileBuild build && build.tryShoot(shootX, shootY)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void drawControlled() {
//            if (true) return;
            if (missilePositions.size == 0) {
                return;
            }
            Draw.draw(Layer.overlayUI, () -> {
                FrameBuffer buffer = renderer.effectBuffer;
                buffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
                buffer.begin(Color.clear);
                Draw.color(Pal.accent);
                missilePositions.each(position -> {
                    if (world.build(position) instanceof MissileSileBuild build) {
                        build.drawActiveTerritory();
                    }
                });
                buffer.end();
                Draw.alpha(0.25f);
                Draw.rect(Draw.wrap(buffer.getTexture()), Core.camera.position.x, Core.camera.position.y, Core.camera.width, -Core.camera.height);
            });
        }

        @Override
        public void drawActiveTerritory() {
            missilePositions.each(position -> {
                if (world.build(position) instanceof MissileSileBuild build) {
                    build.drawActiveTerritory();
                }
            });
        }

        @Override
        public void tapTile(Player player, Tile tile) {

            if (unit == null || !isControlled()) {
                return;
            }
            tryShoot(tile.worldx(), tile.worldy());

        }
    }
}
