package sunset.world.blocks.sandbox;

import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.style.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import kotlin.jvm.internal.Ref.*;
;
import mindustry.ctype.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.io.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
;
import org.jetbrains.annotations.*;
import sunset.utils.*;

import static mindustry.Vars.*;

public class SnMultiSource extends Block{
//    //@Load("@-cross")
    public TextureRegion cross;
//    //@Load("@-liquid")
    public TextureRegion liquidRegion;
//    //@Load("@-item")
    public TextureRegion itemRegion;


    protected float selectScrollItem = 0;
    protected float selectScrollLiquid = 0;

    public SnMultiSource(String name){
        super(name);
        alwaysUnlocked = true;

        update = solid = saveConfig = noUpdateDisabled = true;
        hasItems = hasLiquids = true;
        configurable = outputsLiquid = true;
        displayFlow = false;
        liquidCapacity = 10000f;
        group = BlockGroup.transportation;

        config(Item.class, (MultiSourceBuild tile, Item item) -> tile.data.toggle(item));
        config(Liquid.class, (MultiSourceBuild tile, Liquid liquid) -> tile.data.toggle(liquid));
        config(byte[].class, (MultiSourceBuild tile, byte[] bytes) -> tile.data.fromBytes(bytes));
        configClear((MultiSourceBuild tile) -> tile.data.clear());
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("items");
        removeBar("liquid");
    }


    @Override
    public void drawPlanConfig(BuildPlan req, Eachable<BuildPlan> list){
        Draw.rect(cross, req.drawx(), req.drawy());
   /*     if(req.config instanceof Integer input){
            Point2 data = Point2.unpack(input);
            drawPlanConfigCenter(req, content.item((short)data.x), name + "-center-0");
            drawPlanConfigCenter(req, content.liquid((short)data.y), name + "-center-1");
        }*/
    }

    @Override
    public boolean canReplace(Block other){
        if(other.alwaysReplace) return true;
        return other.replaceable && (other != this || rotate) && this.group != BlockGroup.none && (other.group == BlockGroup.transportation || other.group == BlockGroup.liquids) &&
        (size == other.size || (size >= other.size && ((subclass != null && subclass == other.subclass) || group.anyReplace)));
    }

    public class MultiSourceBuild extends Building{
        final SourceData data = new SourceData();

        @Override
        public void placed(){
            super.placed();
            cdump = 1;
        }

        @Override
        public void draw(){
            super.draw();

            Draw.rect(block.region, x, y);
            Draw.rect(cross, x, y);


            if(data.outputItems.size > 0){
                Draw.color(content.item(Utils.getByIndex(data.outputItems, ((int)Time.time / 10 + id) % data.outputItems.size)).color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }

            if(data.outputLiquids.size > 0){
                Draw.color(content.liquid(Utils.getByIndex(data.outputLiquids, ((int)Time.time / 10 + id + 1) % data.outputLiquids.size)).color);
                Draw.rect(liquidRegion, x, y);
                Draw.color();
            }
        }

        @Override
        public void updateTile(){
            int size = proximity.size;
            if(data.outputItems.size > 0){
                data.outputItems.each(itemId -> {
                    Item item = content.item(itemId);
                    for(int i = 0; i < size; i++){
                        items.set(item, 1);
                        dump(item);
                        items.set(item, 0);
                    }
                });
            }
            if(data.outputLiquids.size > 0){
                data.outputLiquids.each(liquidId -> {
                    Liquid liquid = content.liquid(liquidId);
                    liquids.add(liquid, liquidCapacity);
                    for(int i = 0; i < size; i++){
                        dumpLiquid(liquid);
                    }

                });
                liquids.clear();
            }
        }

        @Override
        public void buildConfiguration(Table table){
            ImageButtonStyle style = new ImageButtonStyle(Styles.cleari);
            style.imageDisabledColor = Color.gray;


            ScrollPane itemPane = buildTable(table, content.items(), this::configure, i -> data.outputItems.contains(i.id));
            itemPane.setScrollYForce(selectScrollItem);
            itemPane.update(() -> {
                selectScrollItem = itemPane.getScrollY();
            });

            table.row();
            table.image(Tex.whiteui).size(40f * 4f, 8f).color(Color.gray).left().top();
            table.row();

            ScrollPane liquidPane = buildTable(table, content.liquids(), this::configure, l -> data.outputLiquids.contains(l.id));
            liquidPane.setScrollYForce(selectScrollLiquid);
            liquidPane.update(() -> {
                selectScrollLiquid = liquidPane.getScrollY();
            });

            table.button(Icon.cancel, style, data::clear).top().size(40f);

        }

        public <T extends UnlockableContent> ScrollPane buildTable(Table table, Seq<T> items, Cons<T> consumer, Boolf<T> checked){

            Table cont = new Table();
            cont.defaults().size(40);

            int i = 0;

            for(T item : items){
                if(!item.unlockedNow()) continue;

                cont.button(Tex.whiteui, Styles.clearTogglei, 24, () -> {
                    consumer.get(item);
                }).checked(b -> checked.get(item))
                .update(b -> b.setChecked(checked.get(item)))
                .get().getStyle().imageUp = new TextureRegionDrawable(item.uiIcon);

                if(i++ % 4 == 3){
                    cont.row();
                }
            }

            //add extra blank spaces so it looks nice
            if(i % 4 != 0){
                int remaining = 4 - (i % 4);
                for(int j = 0; j < remaining; j++){
                    cont.image(Styles.black6);
                }
            }

            ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
            pane.setScrollingDisabled(true, false);

            pane.setOverscroll(false, false);
            table.add(pane).maxHeight(Scl.scl(40 * 5));
            return pane;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                return false;
            }

            return true;
        }

        @Override
        public void drawSelect(){
            super.drawSelect();
            final int width = 3;

            float s = iconSmall / 4f;
            float startOffset = Math.max(Math.min(width, Math.max(data.outputItems.size, data.outputLiquids.size)) - 1, 0) * s;
            float dx = x - size * tilesize / 2f - startOffset,
            dy = y + size * tilesize / 2f;

            IntRef counter = new IntRef();
            Cons2<IntRef, TextureRegion> drawCons = (ref, region) -> {

                float ox = (ref.element % width) * s, oy = -(ref.element / width) * s;

                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(region, dx + ox, dy + oy - 1, s, s);
                Draw.reset();
                Draw.rect(region, dx + ox, dy + oy, s, s);
            };
            data.outputItems.each(id -> {
                drawCons.get(counter, content.item(id).fullIcon);
                counter.element++;
            });
            if(counter.element % width != 0){
                counter.element += width - counter.element % width;
            }
            data.outputLiquids.each(id -> {
                drawCons.get(counter, content.liquid(id).fullIcon);
                counter.element++;
            });

        }

        @Override
        public void configure(Object value){
            if(value instanceof Item item){
                data.toggle(item);
                super.configure(data.toBytes());
                return;
            }
            if(value instanceof Liquid liquid){
                data.toggle(liquid);
                Log.info("liquid: @(@)", liquid, data.outputLiquids.contains(liquid.id));
//                System.out.println("liquid: "+liquid);
                super.configure(data.toBytes());
                return;
            }
            super.configure(value);
        }

        @Override
        public void configureAny(Object value){
            if(value instanceof Item item){
                data.toggle(item);
                super.configureAny(data.toBytes());
                return;
            }
            if(value instanceof Liquid liquid){
                data.toggle(liquid);
                super.configureAny(data.toBytes());
                return;
            }
            if(value instanceof Float power){
                data.outputPower = power;
                super.configureAny(data.toBytes());
                return;
            }
            super.configureAny(value);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }

        @Override
        public byte[] config(){
            return data.toBytes();
        }

        @Override
        public void write(Writes write){
            super.write(write);

            data.write(write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            data.read(read);
        }
    }
}

class SourceData{
    protected IntSet outputItems = new IntSet();
    protected IntSet outputLiquids = new IntSet();
    protected float outputPower = 0;

    public void fromBytes(byte[] bytes){
    }

    public byte[] toBytes(){
        return new byte[0];
    }

    public void write(Writes write){
        write.i(0);

        write.i(outputItems.size);
        outputItems.each(itemId -> {
            TypeIO.writeString(write, content.item(itemId).name);
        });

        write.i(outputLiquids.size);
        outputLiquids.each(liquidId -> {
            TypeIO.writeString(write, content.liquid(liquidId).name);
        });

        write.f(outputPower);
    }

    public void toggle(@NotNull Item item){
        toggleItem(item.id);
    }

    public void toggleItem(int item){
        if(!outputItems.add(item)){
            outputItems.remove(item);
        }
    }

    public void toggle(@NotNull Liquid liquid){
        toggleLiquid(liquid.id);
    }

    public void toggleLiquid(int liquid){
        if(!outputLiquids.add(liquid)){
            outputLiquids.remove(liquid);
        }
    }

    public void read(Reads read){
        int rev = read.i();
        read(read, rev);
    }

    private void read(Reads read, int revision){
        outputLiquids.clear();
        outputItems.clear();

        int itemAmount = read.i();
        for(int i = 0; i < itemAmount; i++){
            String name = TypeIO.readString(read);
            Item item = content.getByName(ContentType.item, name);
            if(item == null){
                name = SaveFileReader.fallback.get(name, name);
                item = content.getByName(ContentType.item, name);
                if(item == null){
                    Log.err("Cannot find item with name \"@\"", name);
                    continue;
                }
            }
            outputItems.add(item.id);
        }


        int liquidAmount = read.i();
        for(int i = 0; i < liquidAmount; i++){
            String name = TypeIO.readString(read);
            Liquid liquid = content.getByName(ContentType.liquid, name);
            if(liquid == null){
                name = SaveFileReader.fallback.get(name, name);
                liquid = content.getByName(ContentType.liquid, name);
                if(liquid == null){
                    Log.err("Cannot find liquid with name \"@\"", name);
                    continue;
                }
            }
            outputLiquids.add(liquid.id);
        }

        outputPower = read.f();
    }

    public void clear(){
        outputLiquids.clear();
        outputItems.clear();
        outputPower = 0f;
    }
}
