package sunset.world.blocks.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.PixmapPacker.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.struct.*;
import arc.util.*;
import kotlin.comparisons.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.units.*;
import mma.*;
import sunset.graphics.*;

import static mindustry.Vars.tilesize;

public class SnUnitFactory extends UnitFactory{
    public SnUnitFactory(String name){
        super(name);
    }

    protected IconPair[] textureRegions = new IconPair[0];


    @Override
    public void load(){
        super.load();
        int[] counter = {0};
        textureRegions = new IconPair[plans.size];
        Seq<IconPair> icons = plans.map(plan -> {
            UnitType unit = plan.unit;
            if(unit.fullIcon == null){
                unit.loadIcon();
            }
            AtlasRegion icon = (AtlasRegion)unit.fullIcon;
            AtlasRegion mask = (AtlasRegion)Core.atlas.find(icon.name + "-animation", ModVars.fullName(icon.name + "-animation"));
            if(!mask.found()) throw new IllegalArgumentException("Cannot find animation texture for region named \"" + icon.name + "\"");
            return new IconPair(icon, mask, counter[0]++);
        });

        PixmapPacker packer = new PixmapPacker(Vars.maxTextureSize, Vars.maxTextureSize, 2, true);

        icons.removeAll(icon -> {
            textureRegions[icon.id] = icon;

            boolean sameTexture = icon.fullIcon.texture == icon.maskIcon.texture;

            PixmapRegion pixmap = Core.atlas.getPixmap(icon.maskIcon);
            if(!sameTexture){
                packer.pack(icon.fullIcon.name + "-copy", Core.atlas.getPixmap(icon.fullIcon));
                packer.pack(icon.maskIcon.name + "-copy", pixmap);
            }
            Pixmap crop = pixmap.crop();
            int[] maxValue = {0};

            crop.each((x, y) -> {
                Tmp.c1.set(crop.get(x, y));
                maxValue[0] = ComparisonsKt.maxOf(maxValue[0], (int)(Tmp.c1.r * 255), (int)(Tmp.c1.g * 255));
            });
            icon.maskSize = maxValue[0];

            return sameTexture;
        });

        if(icons.isEmpty()) return;
        TextureFilter filter = Core.settings.getBool("linear", true) ? TextureFilter.linear : TextureFilter.nearest;
        packer.updateTextureAtlas(Core.atlas, filter, filter, false, false);


        for(IconPair icon : icons){
            icon.fullIcon = Core.atlas.find(icon.fullIcon + "-copy");
            icon.maskIcon = Core.atlas.find(icon.maskIcon + "-copy");
        }

    }

    protected class IconPair{
        public AtlasRegion fullIcon;
        public AtlasRegion maskIcon;
        public final int id;
        public int maskSize = -1;

        public IconPair(AtlasRegion fullIcon, AtlasRegion maskIcon, int id){
            this.fullIcon = fullIcon;
            this.maskIcon = maskIcon;
            this.id = id;
        }
    }

    public class SnUnitFactoryBuild extends UnitFactoryBuild{
        @Override
        public void draw(){
            Draw.rect(region.found() ? region : Blocks.groundFactory.region, x, y);
            Draw.rect(outRegion, x, y, rotdeg());

            if(currentPlan != -1){
                UnitPlan plan = plans.get(currentPlan);
                IconPair icons = textureRegions[currentPlan];
                Draw.draw(Layer.blockOver, () -> {
                    SnShaders.customProgressSprite.set(icons.maskIcon, icons.maskSize, progress / plan.time, icons.fullIcon);
                    Draw.shader(SnShaders.customProgressSprite);
                    Draw.color(Pal.accent);
                    Draw.rect(icons.fullIcon, x, y, rotdeg() - 90);
                    Draw.shader();
                });
//                Draw.draw(Layer.blockOver, () -> Drawf.construct(this, plan.unit, rotdeg() - 90f, progress / plan.time, speedScl, time));
            }

            Draw.z(Layer.blockOver);

            payRotation = rotdeg();
            drawPayload();
            if(!name.equals(ModVars.fullName("ground-factory-test-2"))){

                Draw.z(Layer.blockOver + 0.1f);

                Draw.rect(topRegion, x, y);
            }
        }
    }
}
