package sunset.world.blocks.environment;

import arc.func.Prov;
import mindustry.graphics.MultiPacker;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

public class MockEnvironmentBlock extends Block{
    public Block replacement;
    public Prov<Block> replacementProvider;
    public MockEnvironmentBlock(String name){
        super(name);
        buildVisibility = BuildVisibility.debugOnly;
        inEditor = false;
        outlineIcon = false;
        minfo.mod = null;
        description = "No";
        details = "No";
    }

    @Override
    public boolean isHidden(){
        return true;
    }

    @Override
    public void createIcons(MultiPacker packer){
        //super.createIcons(packer);
    }

    @Override
    public void init(){
        if (replacementProvider!=null){
            replacement=replacementProvider.get();
        }
        if (replacement==null){
            throw new IllegalArgumentException("replacement cannot be null");
        }
        //super.init();
    }
}
