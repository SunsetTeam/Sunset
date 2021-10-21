package sunset.world.blocks.defense.walls;

import mindustry.gen.Bullet;
import mindustry.type.Category;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

public class IndestructibleWall extends Wall{

    public IndestructibleWall(String name){
        super(name);
        health = 1;
        absorbLasers = true;
        chanceDeflect = 1;
        buildVisibility = BuildVisibility.sandboxOnly;
        category = Category.defense;
    }
    public class IndestructibleBuild extends WallBuild{
        @Override
        public float handleDamage(float amount){
            return 0;
        }

        @Override
        public boolean collision(Bullet bullet){
            super.collision(bullet);
            return true;
        }
    }
}
