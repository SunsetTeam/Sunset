package sunset.world.blocks.production;

import arc.*;
import arc.math.Interp;
import arc.graphics.g2d.*;
import mindustry.world.blocks.production.GenericSmelter;

//TODO удалить?
public class RotatorSmelter extends GenericSmelter{
  protected TextureRegion rotatorRegion;
  
  public RotatorSmelter(String name){
      super(name);
  }

  @Override
  public void load(){
      super.load();
      rotatorRegion = Core.atlas.find(name + "-rotator");
  }
    
  @Override
  public TextureRegion[] icons(){
      return new TextureRegion[]{rotatorRegion};
  }
  
  public class RotatorSmelterBuild extends SmelterBuild{

      @Override
      public void draw(){
            Draw.rect(rotatorRegion, this.x, this.y, 360 * Interp.pow3Out.apply(this.progress));
      }
  }
}