package sunset.world.blocks.environment;

import arc.graphics.g2d.TextureRegion;
import mindustry.type.*;
import mindustry.world.blocks.environment.OreBlock;

public class ModOreBlock extends OreBlock {
  
  public ModOreBlock(String name, Item itemDrop){
    super(name);
    this.itemDrop = itemDrop;
  }
  
  @Override
  public TextureRegion[] editorVariantRegions() {
      return super.editorVariantRegions();
  }

  @Override
  public TextureRegion editorIcon() {
      return super.editorIcon();
  }
}