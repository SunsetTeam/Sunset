package sunset.world.blocks.laser;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;

import mindustry.Vars;
import mindustry.annotations.Annotations;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

public class LaserNode extends LaserBlock{
    /** Enable range restrictions. */
    public boolean enableRange = true;
    /** Max range of node connection. */
    public final float range = 10 * Vars.tilesize;
    /** Max laser charge at which node will overheat and destroy. This will prevent abusing lasers in some ways. */
    public float maxCharge = 20f;
    public LaserNode(String name) {
        super(name);
        configurable = true;
        consumesPower = false;
        outputsPower = false;
        update = true;

        solid = true;
        config(Integer.class, (LaserBlockBuild tile, Integer n) -> {
            //link to other laser block
            Point2 a = Point2.unpack(n);
            Log.info("X: @\nY: @", a.x * Vars.tilesize, a.y * Vars.tilesize);
            LaserBlockBuild build = (LaserBlockBuild) Vars.world.build(n);
            tile.laserModule.linkTo(build);
        });
    }
    public boolean linkValid(LaserBlockBuild build, LaserBlockBuild other){
        //debug, sorry for mess
        Log.info("--------");
        float tx = build.x,
                ty = build.y,
                ox = other.x,
                oy = other.y;
        //check for laser consumption
        if(((LaserBlock) other.block).consumesLaser) {
            Log.info("Consumes laser");
            //check for staying in range
            Tmp.v1.set(tx, ty);
            Tmp.v2.set(ox, oy);
            Tmp.v2.sub(Tmp.v1);
            if (Tmp.v2.len() < range) {
                Log.info("In range");
                //check for staying on 'straight' line with other block
                float offset = Vars.tilesize / 2f * Math.max(build.block().size, other.block().size);
                if(Math.abs(ox - tx) < offset || Math.abs(oy - ty) < offset){
                    Log.info("Link confirmed");
                    return true;
                }
                else
                    Log.info("Coordinates are not the same, x: @, y: @, ox: @, oy: @", tx, ty, ox, oy);
            }
            else
                Log.info("Not in range");
        }
        else
            Log.info("Don't consume laser");
        return false;
    }
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Draw.color(Pal.placing);
        //draw range if we can
        if(enableRange){
            Drawf.circles(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range);
        }
    }
    public class LaserNodeBuild extends LaserBlockBuild{
        @Override
        public void drawSelect(){
            super.drawSelect();
            Draw.color(Pal.placing);
            //draw range if we can
            if(enableRange){
                Drawf.circles(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range);
            }
        }
        @Override
        public void drawConfigure(){
            Drawf.select(x, y, tile.block().size * Vars.tilesize / 2f + 2f, Pal.accent);
            for (LaserLink l : laserModule.output){
                LaserBlockBuild link = l.build;
                Drawf.select(link.x, link.y, link.block().size * Vars.tilesize / 2f + 2f + Mathf.absin(Time.time, 4f, 1f), Pal.place);
            }
            for (LaserLink l : laserModule.input){
                LaserBlockBuild link = l.build;
                Drawf.select(link.x, link.y, link.block().size * Vars.tilesize / 2f + 2f + Mathf.absin(Time.time, 4f, 1f), Pal.breakInvalid);
            }
        }
        @Override
        public boolean onConfigureTileTapped(Building other) {
            if (this == other){
                deselect();
                return false;
            }
            if(other instanceof LaserBlockBuild b){
                //check
                if(linkValid(this, b))
                    configure(b.pos());
                return false;
            }
            return true;
        }
        //save
        @Override
        public Object config(){
            return laserModule.output;
        }
    }
}
