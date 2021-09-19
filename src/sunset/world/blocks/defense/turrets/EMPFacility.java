package sunset.world.blocks.defense.turrets;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.logic.LAccess;
import mindustry.ui.Bar;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.defense.turrets.Turret;
import sunset.utils.Utils;

import static arc.Core.bundle;

/**
 * You cannot control this block. Minimum size 3 tile.
 * */
public class EMPFacility extends Turret{
    public int multiplier = 1;
    public BulletType shootType;
    public float powerUse;
    public boolean logicConfigurable = false;
    //public TextureRegion[] sideRegions = new TextureRegion[4];
    //public float pullTime = 60f, closeTime = 90f;
    //public float xOpen = 2f, yOpen = -3f;

    public Cons<EMPBuild> heatDrawer = tile -> {
        if(tile.heat <= 0.00001f) return;

        Draw.color(heatColor, tile.heat);
        Draw.blend(Blending.additive);
        Draw.rect(heatRegion, tile.x, tile.y);
        Draw.blend();
        Draw.color();
    };

    public EMPFacility(String name){
        super(name);
        shootCone = 720;
        shots = 360 * multiplier;
        rotateSpeed = 0;
        powerUse = 1;
        reloadTime = 300;
        size = 3;
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add("sunset-reload", (EMPBuild entity) -> new Bar(
                () -> bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reload / reloadTime) * 100f)),
                () -> entity.team.color,
                () -> Mathf.clamp(entity.reload / reloadTime)
        ));

        bars.add("sunset-charge", (EMPBuild entity) -> new Bar(
                () -> bundle.format("bar.sunset-charge", Utils.stringsFixed(Mathf.clamp(entity.charge) * 100f)),
                () -> Color.gold,
                () -> entity.charge
        ));
    }

    @Override
    public void load(){
        super.load();
        /*for(int i = 0; i < 3; i++){
            sideRegions[i] = atlas.find(name + "-side-" + i);
        }*/
    }

    @Override
    public void init(){
        consumes.powerCond(powerUse, TurretBuild::isShooting);
        super.init();
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[] {/*Core.atlas.find(name + "-icon")*/region};
    }

    public class EMPBuild extends TurretBuild implements ControlBlock{
        protected float charge;
        //protected float chargeTimer;

        @Override
        public boolean canControl(){
            return false;
        }

        @Override
        public void draw(){  //animation will be later
            /*float totalTime = chargeTime + closeTime;
            float openAmount = Mathf.curve(chargeTimer, 0f, pullTime);
            float closeAmount = Mathf.curve(chargeTimer, chargeTime, totalTime);
            float openX = xOpen * Interp.pow2Out.apply(openAmount) - xOpen * Interp.pow2In.apply(closeAmount);
            float openY = yOpen * Interp.pow5In.apply(openAmount) - yOpen * Interp.pow5Out.apply(closeAmount);

            Tmp.v1.trns(rotation - 90, -openX, openY - recoil);
            Tmp.v2.trns(rotation - 90, openX, openY - recoil);
            float[] sXPre = {Tmp.v1.x, Tmp.v2.x};
            float[] sYPre = {Tmp.v1.y, Tmp.v2.y};
            float[] sX = {sXPre[0] + x, sXPre[1] + x};
            float[] sY = {sYPre[0] + y, sYPre[1] + y};*/

            Draw.rect(baseRegion, x, y);
            Draw.color();

            Draw.z(Layer.turret);

            //tr2.trns(rotation, -recoil);

            Drawf.shadow(region, x - elevation, y - elevation);
            drawer.get(this);

            /*for(int i = 0; i < 2; i++){
                Draw.rect(sideRegions[i], sX[i], sY[i], rotation - 90f);
            }*/

            if(heatRegion != Core.atlas.find("error")){
                heatDrawer.get(this);
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();
            unit.ammo(power.status * unit.type().ammoCapacity);
            if(charging){
                charge = Mathf.clamp(charge + Time.delta / chargeTime);
            }else{
                charge = 0;
            }
        }

        @Override
        public BulletType useAmmo(){
            return shootType;
        }

        @Override
        public boolean hasAmmo(){
            return true;
        }

        @Override
        public BulletType peekAmmo(){
            return shootType;
        }

        public int size(){
            if(size < 3) return size = 3;
            return size;
        }
    }
}
