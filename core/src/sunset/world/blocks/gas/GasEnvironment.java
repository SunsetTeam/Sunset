package sunset.world.blocks.gas;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.g2d.PixmapRegion;
import arc.util.Nullable;
import gas.type.Gas;
import gas.world.GasBlock;
import mindustry.annotations.Annotations;
import mindustry.graphics.MultiPacker;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public class GasEnvironment extends GasBlock {
    public @Nullable Gas gasDrop = null;
    public float gasMultiplier = 1f;
    /** whether this block is liquid. */
    public boolean isGas;

    public GasEnvironment(String name, Gas gasEnv){
        super(name);
        this.localizedName = gasEnv.localizedName;
        this.gasDrop = gasEnv;
        this.variants = 3;
        this.mapColor.set(gasEnv.color);
        this.useColor = true;
    }

    public GasEnvironment(Gas gasEnv){
        this("gas-" + gasEnv.name, gasEnv);
    }

    public GasEnvironment(String name){
        super(name);
        this.useColor = true;
        variants = 3;
    }

    public void setup(Gas gasEnv){
        this.localizedName = gasEnv.localizedName;
        this.gasDrop = gasEnv;
        this.mapColor.set(gasEnv.color);
    }

    @Override
    @Annotations.OverrideCallSuper
    public void createIcons(MultiPacker packer){
        for(int i = 0; i < variants; i++){
            PixmapRegion shadow = Core.atlas.getPixmap(gasDrop.name + (i + 1));
            Pixmap image = shadow.crop();

            int offset = image.width / tilesize - 1;
            int shadowColor = Color.rgba8888(0, 0, 0, 0.3f);

            for(int x = 0; x < image.width; x++){
                for(int y = offset; y < image.height; y++){
                    if(shadow.getA(x, y) == 0 && shadow.getA(x, y - offset) != 0){
                        image.setRaw(x, y, shadowColor);
                    }
                }
            }

            packer.add(MultiPacker.PageType.environment, name + (i + 1), image);
            packer.add(MultiPacker.PageType.editor, "editor-" + name + (i + 1), image);

            if(i == 0){
                packer.add(MultiPacker.PageType.editor, "editor-block-" + name + "-full", image);
                packer.add(MultiPacker.PageType.main, "block-" + name + "-full", image);
            }
        }
    }

    @Override
    public void init(){
        super.init();

        if(gasDrop != null){
            setup(gasDrop);
        }else{
            throw new IllegalArgumentException(name + " must have an item drop!");
        }
    }

    @Override
    public String getDisplayName(Tile tile){
        return gasDrop.localizedName;
    }
}
