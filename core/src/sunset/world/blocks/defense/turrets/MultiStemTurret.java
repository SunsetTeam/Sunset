package sunset.world.blocks.defense.turrets;

import arc.math.Mathf;
import mindustry.entities.bullet.BulletType;

public class MultiStemTurret extends ModItemTurret{
    public float[] shootLocates;

    public MultiStemTurret(String name){
        super(name);
    }

    public class MultiStemTurretBuild extends ModItemTurretBuild{
        @Override
        protected void shoot(BulletType type){
            for(float shootLocate: shootLocates){
                if(hasAmmo()){
                    tr.trns(rotation - 90, shootLocate, shootLength - recoil);
                    bullet(type, rotation + Mathf.range(inaccuracy + type.inaccuracy));
                    effects();
                    useAmmo();
                }
            }
        }
    }
}
