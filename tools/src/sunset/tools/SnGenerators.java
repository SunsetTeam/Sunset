package sunset.tools;

import arc.*;
import arc.files.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.struct.ObjectMap.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mma.tools.*;
import mma.type.*;
import sunset.content.blocks.*;
import sunset.type.*;

import java.lang.reflect.*;

import static mindustry.Vars.content;
import static mma.tools.ModImagePacker.processor;
import static mma.tools.gen.MindustryImagePacker.*;

public class SnGenerators extends ModGenerators{
    private static DrawBlock[] flatDrawBlock(DrawBlock drawBlock){
        if(drawBlock instanceof DrawMulti){
            DrawMulti multi = (DrawMulti)drawBlock;

            DrawBlock[] drawers = multi.drawers;
            if(Structs.contains(drawers, it -> it instanceof DrawMulti)){
                Seq<DrawBlock> seq = new Seq<>();
                for(DrawBlock drawer : drawers){
                    seq.addAll(flatDrawBlock(drawer));
                }
                return seq.toArray(DrawBlock.class);
            }
            return drawers;
        }else{
            return new DrawBlock[]{drawBlock};
        }
    }

    @Override
    protected void run(){
        super.run();
        generate("missileIcons", this::missileIcons);
    }

    @Override
    protected void blockIcons(){
        ObjectMap<Block, Seq<DrawBlock>> drawBlocks = Vars.content.blocks().asMap(it->it, it -> {
            Class<?> clazz = it.getClass();
            Seq<Field> select = Seq.with(clazz.getFields()).select(it2 -> it2.getType() == DrawBlock.class);

            return select.map(it2 -> Reflect.<DrawBlock>get(it, it2)).flatMap(it2 -> Seq.with(flatDrawBlock(it2)));
        });
        for(Block block : drawBlocks.keys().toSeq()){
            if (drawBlocks.get(block).isEmpty()) drawBlocks.remove(block);
        }
        System.out.println(drawBlocks);
        for(Entry<Block, Seq<DrawBlock>> entry : drawBlocks){
            for(DrawBlock drawBlock : entry.value){
                if(drawBlock instanceof ImageDrawBlockGenerator){
                    entry.key.load();
                    ImageDrawBlockGenerator generator = (ImageDrawBlockGenerator)drawBlock;
                    Log.info("@: @",entry.key,drawBlock);
                    generator.preGenerate(entry.key,SnImagePacker.snProcessor);
                }
            }
//            entry.key.load();
        }
//        System.out.println(SnLasers.laserNode.getGeneratedIcons().length);
//        System.out.println(SnLasers.laserSource.getGeneratedIcons().length);
//        System.out.println(SnLasers.laserKiln.getGeneratedIcons().length);
        SnImagePacker.processor=SnImagePacker.snProcessor;
        Log.info("{\n\t@\n}",cache.keys().toSeq().toString(",\n\t"));
        try {
          this.blockIconsOld();
       } catch (Exception e) {
//           System.out.println(cache.keys().toSeq().toString("\n"));
         throw new RuntimeException(e);
       }

        for(Entry<Block, Seq<DrawBlock>> entry : drawBlocks){
            for(DrawBlock drawBlock : entry.value){

                if(drawBlock instanceof ImageDrawBlockGenerator){
                    ImageDrawBlockGenerator generator = (ImageDrawBlockGenerator)drawBlock;
//                    Log.info("@: @",entry.key,entry.value);
                    generator.postGenerate(entry.key,SnImagePacker.snProcessor);
                }
            }
        }
    }

//    @Override
    protected void blockIconsOld(){
        for(Block block : content.blocks()){
            if(block.isAir() || block instanceof ConstructBlock || block instanceof OreBlock || block instanceof LegacyBlock)
                continue;

            block.load();
            block.loadIcon();

            TextureRegion[] regions = block.getGeneratedIcons();

            if(block.variants > 0 || block instanceof Floor){
                for(TextureRegion region : block.variantRegions()){
                    GenRegion gen = (GenRegion)region;
                    if(gen.path == null) continue;
                    gen.path.copyTo(Fi.get("../editor/editor-" + gen.path.name()));
                }
            }

            for(TextureRegion region : block.makeIconRegions()){
                GenRegion gen = (GenRegion)region;
                save(get(region).outline(block.outlineColor, block.outlineRadius), gen.name + "-outline");
            }

            Pixmap shardTeamTop = null;

            if(block.teamRegion.found()){
                Pixmap teamr = get(block.teamRegion);

                for(Team team : Team.all){
                    if(team.hasPalette){
                        Pixmap out = new Pixmap(teamr.width, teamr.height);
                        teamr.each((x, y) -> {
                            int color = teamr.getRaw(x, y);
                            int index = color == 0xffffffff ? 0 : color == 0xdcc6c6ff ? 1 : color == 0x9d7f7fff ? 2 : -1;
                            out.setRaw(x, y, index == -1 ? teamr.getRaw(x, y) : team.palette[index].rgba());
                        });
                        save(out, block.name + "-team-" + team.name);

                        if(team == Team.sharded){
                            shardTeamTop = out;
                        }
                    }
                }
            }

            if(regions.length == 0){
                continue;
            }

            try{
                Pixmap last = null;
                if(block.outlineIcon){
                    GenRegion region = (GenRegion)regions[block.outlinedIcon >= 0 ? block.outlinedIcon : regions.length - 1];
                    Pixmap base = get(region);
                    Pixmap out = last = base.outline(block.outlineColor, block.outlineRadius);

                    //do not run for legacy ones
                    if(block.outlinedIcon >= 0){
                        //prevents the regions above from being ignored/invisible/etc
                        for(int i = block.outlinedIcon + 1; i < regions.length; i++){
                            out.draw(get(regions[i]), true);
                        }
                    }


                    if(false){
                        region.path.delete();
                        save(out, block.name);
                    }
                }

                if(!regions[0].found()){

                    Log.err("WARNING: Skipping block " + block.name + ": Cannot find \"@\" region for full icon",((GenRegion)regions[0]).name);
                    continue;
                }
                boolean selfGenerator = block instanceof ImageGenerator;
                Pixmap image = get(regions[0]);

                int i = 0;

                for(TextureRegion region : regions){
                    i++;
                    if(i == 1 && selfGenerator){
                        image.draw(((ImageGenerator)block).generate(get(regions[0]), processor));
                    }else if(i != regions.length || last == null){
                        image.draw(get(region), true);
                    }else{
                        image.draw(last, true);
                    }

                    //draw shard (default team top) on top of first sprite
                    if(region == block.teamRegions[Team.sharded.id] && shardTeamTop != null){
                        image.draw(shardTeamTop, true);
                    }
                }
                if(!(regions.length == 1 && regions[0] == Core.atlas.find(block.name) && shardTeamTop == null) || selfGenerator){
                    save(image, /*"block-" +*/ block.name + "-full");
                } else{
//                    Log.info("creating full icon for \"@\" is unnecessary",block.name);
                }

                save(image, "../editor/" + block.name + "-icon-editor");

                if(block.buildVisibility != BuildVisibility.hidden){
                    saveScaled(image, block.name + "-icon-logic", logicIconSize);
                }
                saveScaled(image, "../ui/block-" + block.name + "-ui", Math.min(image.width, maxUiIcon));


            }catch(NullPointerException e){
                Log.err("Block &ly'@'&lr has an null region!", block);
            }
        }
    }
    protected void missileIcons(){
        for(MissileType type : MissileType.missileTypes){
            TextureRegion rocketRegion = type.rocketRegion;
            if(!rocketRegion.found()){
                Log.warn("Cannot find missileType region with name @", type.spriteName);
                continue;
            }
            Pixmap region = get(rocketRegion);
            int radius = type.outlineRadius + 1;
            Pixmap pixmap = new Pixmap(region.width + radius * 2, region.height + radius * 2);
            pixmap.draw(region, radius, radius);

            pixmap = pixmap.outline(type.outlineColor, type.outlineRadius);
            save(pixmap, type.spriteName + "-outline");
        }
    }
}
