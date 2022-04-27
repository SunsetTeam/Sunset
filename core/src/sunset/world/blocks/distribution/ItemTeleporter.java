package sunset.world.blocks.distribution;

import arc.Core;
import arc.func.Boolf;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.*;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class ItemTeleporter extends Block {
    public int maxLink = 3;

    public ItemTeleporter(String name) {
        super(name);
        update = true;
        config(Integer.class, (ItemTeleporterBuild tile, Integer point) -> {
            tile.addLink(point);
        });
    }

    public void setStats() {
        super.setStats();
        stats.add(Stat.powerConnections, (float)(maxLink - 1), StatUnit.none);
    }

    public void setBars() {
        super.setBars();
        bars.add("connections", (bar) -> new Bar(() -> Core.bundle.format("bar.powerlines", bar.block.size, maxLink), () -> Pal.accent, () -> (float)bar.block.size / (float)maxLink));
    }

    public class ItemTeleporterBuild extends Building {
        public int linkRotation = 0;
        public Seq<Integer> b = new Seq();

        public void addLink(int point) {
            if (b.contains(point)) {
                b.remove(b.indexOf(point));
            } else if (b.size <= maxLink - 1) {
                b.add(point);
            }
        }

        public boolean onConfigureTileTapped(Building other) {
            if (pos() != other.pos() && team == other.team && block.name == other.block.name) {
                configure(other.pos());
                return false;
            } else {
                return block.name.equals(other.block.name) ? true : true;
            }
        }

        public void update() {
            int rot;
            int i;
            if (b.size != 0) {
                if (items.first() != null) {
                    rot = linkRotation;

                    for(i = 0; i < b.size; ++i) {
                        Building build = Vars.world.build((Integer)b.get((i + rot) % b.size));
                        if (build != null && items.first() != null && build.acceptItem(this, items.first())) {
                            build.handleItem(this, items.first());
                            items.remove(items.first(), 1);
                            linkRotation = (linkRotation + 1) % b.size;
                        }
                    }

                    linkRotation = (linkRotation + 1) % b.size;
                }
            } else {
                rot = Math.min(Mathf.ceil((float)items.total() / 4.0F), 4);

                for(i = 0; i < rot && dump((Item)null); ++i) {
                }

            }
        }

        public void drawConfigure() {
            try {
                super.drawConfigure();
                float sin = Mathf.absin(Time.time, 6.0F, 1.0F);
                Draw.color(Pal.accent);
                Lines.square(x, y, ((float)block.size / 2.0F + 1.0F) * 8.0F + sin - 5.0F);

                for(int i = 0; i < b.size; ++i) {
                    Building build = Vars.world.build((Integer)b.get(i));
                    Draw.color(Pal.place);
                    Lines.square(build.x, build.y, ((float)build.block.size / 2.0F + 1.0F) * 8.0F + sin - 5.0F);
                }
            } catch (Exception var4) {
            }

        }

        public void handleItem(Building source, Item item) {
            if (b.size != 0 && this != source) {
                if (Vars.world.build((Integer)b.get(linkRotation % b.size)) != null && Vars.world.build((Integer)b.get(linkRotation % b.size)).acceptItem(source, item)) {
                    Vars.world.build((Integer)b.get(linkRotation % b.size)).handleItem((Building)(source instanceof ItemTeleporterBuild ? source : this), item);
                } else {
                    items.add(item, 1);
                }

                linkRotation = (linkRotation + 1) % b.size;
            } else {
                items.add(item, 1);
            }
        }

        public boolean acceptItem(Building source, Item item) {
            if (item != null) {
                return items.get(item) < getMaximumAccepted(item);
            } else {
                return false;
            }
        }
    }
}