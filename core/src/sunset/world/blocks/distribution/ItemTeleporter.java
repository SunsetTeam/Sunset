package sunset.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class ItemTeleporter extends Block{
    public int maxLink = 3;
    public float range = 10f;

    @SuppressWarnings("Convert2MethodRef")
    public ItemTeleporter(String name){
        super(name);
        update = true;
        config(Integer.class, (ItemTeleporterBuild tile, Integer point) -> {
            tile.addLink(point);
        });
    }

    public void setStats(){
        super.setStats();
        stats.add(Stat.powerConnections, (float)(maxLink - 1), StatUnit.none);
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
    }

    public void setBars(){
        super.setBars();
        bars.add("connections", entity -> new Bar(
        () -> Core.bundle.format("bar.powerlines", entity.block.size, maxLink),
        () -> Pal.accent, () -> entity.block.size / (float)maxLink));
    }

    public class ItemTeleporterBuild extends Building implements Ranged{
        public int linkRotation = 0;
        public IntSeq links = new IntSeq();

        public void addLink(int point){
            if(links.contains(point)){
                links.removeValue(links.indexOf(point));
            }else if(links.size <= maxLink - 1){
                links.add(point);
            }
        }

        public boolean onConfigureTileTapped(Building other){
            if(pos() != other.pos() && team == other.team && block == other.block){
                configure(other.pos());
                return false;
            }else{
                return block == other.block ? true : true;
            }
        }

        public void update(){
            int rot;
            int i;
            if(links.size != 0){
                if(items.first() != null){
                    rot = linkRotation;

                    for(i = 0; i < links.size; ++i){
                        Building build = Vars.world.build(links.get((i + rot) % links.size));
                        if(build != null && items.first() != null && build.acceptItem(this, items.first())){
                            build.handleItem(this, items.first());
                            items.remove(items.first(), 1);
                            linkRotation = (linkRotation + 1) % links.size;
                        }
                    }

                    linkRotation = (linkRotation + 1) % links.size;
                }
            }else{
                rot = Math.min(Mathf.ceil((float)items.total() / 4.0F), 4);

                for(i = 0; i < rot && dump((Item)null); ++i){
                }

            }
        }

        public void drawConfigure(){
            try{
                super.drawConfigure();
                float sin = Mathf.absin(Time.time, 6.0F, 1.0F);
                Draw.color(Pal.accent);
                Lines.square(x, y, ((float)block.size / 2.0F + 1.0F) * 8.0F + sin - 5.0F);

                for(int i = 0; i < links.size; ++i){
                    Building build = Vars.world.build(links.get(i));
//                    if(build == null) continue;
                    Draw.color(Pal.place);
                    Lines.square(build.x, build.y, ((float)build.block.size / 2.0F + 1.0F) * 8.0F + sin - 5.0F);
                }
            }catch(Exception var4){
            }

        }

        public void handleItem(Building source, Item item){
            if(links.size != 0 && this != source){
                if(Vars.world.build(links.get(linkRotation % links.size)) != null && Vars.world.build(links.get(linkRotation % links.size)).acceptItem(source, item)){
                    Vars.world.build(links.get(linkRotation % links.size)).handleItem((source instanceof ItemTeleporterBuild ? source : this), item);
                }else{
                    items.add(item, 1);
                }

                linkRotation = (linkRotation + 1) % links.size;
            }else{
                items.add(item, 1);
            }
        }

        public boolean acceptItem(Building source, Item item){
            if(item != null){
                return items.get(item) < getMaximumAccepted(item);
            }else{
                return false;
            }
        }

        @Override
        public float range(){
            return range;
        }
    }
}