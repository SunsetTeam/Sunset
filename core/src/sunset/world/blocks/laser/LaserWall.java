package sunset.world.blocks.laser;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.struct.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;

import static mindustry.Vars.tilesize;

public class LaserWall extends LaserBlock{
    Queue<LaserWallBuild> queue = new Queue<>();
    public float maxResist = 10f;
    public LaserWall(String name) {
        super(name);
        update = true;
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("resistBar", (LaserWallBuild w) -> new Bar(
            ()-> Core.bundle.format("bar.laser-resist", w.resist, maxResist),
            ()-> Pal.accent,
            ()-> w.resist / maxResist
        ));
    }
    public class LaserWallBuild extends LaserBuild{
        float resist = 0f,
                drawResist = resist;
        float additiveCharge = 0f;
        LaserWallChain laserChain;

        @Override
        public Building init(Tile tile, Team team, boolean shouldAdd, int rotation){
            laserChain = new LaserWallChain(this);
            return super.init(tile, team, shouldAdd, rotation);
        }

        @Override
        public void onProximityAdded(){
            super.onProximityAdded();
            laserChain.updateChain();
        }

        @Override
        public void onProximityRemoved(){
            super.onProximityRemoved();
            for(var b : proximity){
                if(b instanceof LaserWallBuild w){
                    Log.info("onProximityRemoved: @", w.id);
                    w.laserChain.updateChain();
                }
            }
        }
        @Override
        public void update(){
            super.update();
            laserChain.updateCharge();
            resist = Mathf.map(additiveCharge, 0f, block().heatLaserLimit, 0f, maxResist);
            drawResist = Mathf.lerp(drawResist, resist, 0.1f);
        }

        @Override
        public void draw(){
            super.draw();
            //drawPlaceText("Multiplier: " + Mathf.round(resist, 1)  + "\n" + id + "\nAmount: " + laserNet.chainedWalls.size, tileX(), tileY(), true);
            //drawPlaceText(laserNet.chainUpdater ? "updater" : "", tileX(), tileY(), true);
            Draw.draw(Layer.shields - 0.001f, ()-> {
                Draw.color(Pal.lancerLaser);
                TextureRegion w = Core.atlas.white();
                float size = block().size * tilesize * Math.min(drawResist, 1f);
                Draw.rect(w, x, y, size, size);
            });
        }

        @Override
        public void damage(float damage){
            if(dead()) return;

            float dm = Vars.state.rules.blockHealth(team);
            lastDamageTime = Time.time;

            if(Mathf.zero(dm)){
                damage = health + 1;
            }else{
                damage = Damage.applyArmor(damage, block.armor) / (dm + resist);
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
        public LaserWall block(){
            return (LaserWall) this.block;
        }
    }
}
