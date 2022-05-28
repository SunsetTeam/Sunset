package sunset.world.blocks.laser;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Call;

public class LaserWall extends LaserBlock{
    public LaserWall(String name) {
        super(name);
        update = true;
        configurable = true;

    }
    public class LaserWallBuild extends LaserBlockBuild{
        float multiplier = 1f;

        @Override
        public void draw(){
            super.draw();
            drawPlaceText("Multiplier: " + multiplier, tileX(), tileY(), true);
        }

        @Override
        public void damage(float damage){
            if (!this.dead()) {
                float dm = Vars.state.rules.blockHealth(this.team);
                if (Mathf.zero(dm)) {
                    damage = this.health + 1.0F;
                } else {
                    damage /= (dm + multiplier);
                }

                Call.tileDamage(this, this.health - this.handleDamage(damage));
                if (this.health <= 0.0F) {
                    Call.tileDestroyed(this);
                }

            }
        }

        @Override
        public void buildConfiguration(Table table){
            table.slider(1f, 10f, 0.1f, f -> {
                multiplier = f;
            });
        }
    }
}
