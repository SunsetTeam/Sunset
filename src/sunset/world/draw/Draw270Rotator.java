package sunset.world.draw;

import arc.*;
import arc.Core;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.math.Interp;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.world.draw.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawBlock;

public class Draw270Rotator extends DrawBlock{
  protected TextureRegion rotatorRegion;
  protected TextureRegion top;
  
  @Override
  public void draw(GenericCrafter.GenericCrafterBuild entity){
        Draw.rect(entity.block.region, entity.x, entity.y);
        Draw.rect(rotatorRegion, entity.x, entity.y, 270 * Interp.pow3Out.apply(entity.progress));
        Draw.rect(top, entity.x, entity.y);
        }

  @Override
  public void load(Block block){
      rotatorRegion = Core.atlas.find(block.name + "-rotator");
      top = Core.atlas.find(block.name + "-top");
  }
    
  @Override
  public TextureRegion[] icons(Block block){
      return new TextureRegion[]{block.region, rotatorRegion};
  }
}