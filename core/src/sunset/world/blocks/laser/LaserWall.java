package sunset.world.blocks.laser;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.*;
import mindustry.gen.Call;

public class LaserWall extends LaserBlock{
    public LaserWall(String name) {
        super(name);
        update = true;
        configurable = true;

    }
    public class LaserWallBuild extends LaserBuild{
        float multiplier = 1f;

        @Override
        public void draw(){
            super.draw();
            drawPlaceText("Multiplier: " + multiplier, tileX(), tileY(), true);
        }

        @Override
        public void damage(float damage){
            if(dead()) return;

            float dm = Vars.state.rules.blockHealth(team);
            lastDamageTime = Time.time;

            if(Mathf.zero(dm)){
                damage = health + 1;
            }else{
                damage = Damage.applyArmor(damage, block.armor) / dm;
            }

            //TODO handle this better on the client.
            if(!Vars.net.client()){
                health -= handleDamage(damage);
            }

            healthChanged();

            if(health <= 0){
                Call.buildDestroyed(self());
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
