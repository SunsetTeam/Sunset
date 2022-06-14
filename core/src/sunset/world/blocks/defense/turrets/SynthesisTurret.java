package sunset.world.blocks.defense.turrets;

import acontent.world.meta.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import sunset.*;
import sunset.content.*;
import sunset.utils.*;
import sunset.world.meta.*;

import static mindustry.Vars.*;

public class SynthesisTurret extends ItemTurret{
    public float powerUse;

    public float maxReloadMultiplier = 0.5f;
    public float speedupPerShot = 0.125f;
    public float slowReloadTime = 70;

    public float primaryArmor = 0;
    public float maxShield = health;
    public float regenCooldown = 2 * Time.toSeconds;
    public float regenAmount = 1;
    public float secondaryArmor = 0;

    public AStats aStats = new AStats();

    public SynthesisTurret(String name){
        super(name);
        unitSort = (u, x, y) -> -u.armor;
        update = true;
        sync = true;
        stats = aStats.copy(stats);
    }

    @Override
    public void init(){
        consumePowerCond(powerUse, TurretBuild::isShooting);
        super.init();
    }

    @Override
    public void setStats(){
        //general
        aStats.add(Stat.health, health, StatUnit.none);
        aStats.add(Stat.size, "@x@", size, size);
        aStats.add(Stat.buildTime, buildCost / 60, StatUnit.seconds);
        aStats.add(Stat.buildCost, StatValues.items(false, requirements));
        //function
        aStats.add(Stat.shootRange, range / tilesize, StatUnit.blocks);
        aStats.add(SnStat.minimalRange, minRange / tilesize, StatUnit.blocks);
        aStats.add(Stat.inaccuracy, (int)inaccuracy, StatUnit.degrees);
        aStats.add(Stat.reload, 60f / (reload) * (shoot.shots), StatUnit.perSecond);
        aStats.add(Stat.targetsAir, targetAir);
        aStats.add(Stat.targetsGround, targetGround);
        aStats.add(Stat.ammoUse, ammoPerShot, StatUnit.perShot);
        aStats.add(Stat.ammo, StatValues.ammo(ammoTypes));
        //armor
        aStats.add(SnStat.primaryArmor, primaryArmor, StatUnit.none);
        aStats.add(SnStat.secondaryArmor, secondaryArmor, StatUnit.none);
        //power
        aStats.add(Stat.powerUse, powerUse * 60, StatUnit.powerSecond);
        //liquids
        aStats.add(Stat.liquidCapacity, liquidCapacity, StatUnit.liquidUnits);
        //optional
        aStats.add(Stat.booster, StatValues.boosters(reload, coolant.amount, coolantMultiplier, true, l -> liquidFilter[l.id]));

        //super.setStats();
        //aStats.add(SnStat.primaryArmor, primaryArmor, StatUnit.none);
        //aStats.add(SnStat.secondaryArmor, secondaryArmor, StatUnit.none);
        //aStats.add(SnStat.minimalRange, minRange / tilesize, StatUnit.blocks);
    }

    @Override
    public void setBars(){
        super.setBars();
        SnVars.settings.registerReloadBarBlock(this, (SynthesisBuild entity) -> new Bar(
        () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
        () -> entity.team.color,
        () -> Mathf.clamp(entity.reloadCounter / reload)
        ));
        addBar("shield", (SynthesisBuild entity) -> new Bar(Core.bundle.format("bar.shield"),
        Pal.accent,
        () -> entity.shield / health));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
        if(minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
    }

    public class SynthesisBuild extends ItemTurretBuild{
        public float speedupScl = 0f;
        public float slowDownReload = 0f;
        public float shieldAlpha;
        float shield;
        float heat = 0f;

        @Override
        public void updateTile(){
            super.updateTile();
            if(this.health < maxHealth / 10){
                if(slowDownReload >= 1f){
                    slowDownReload -= Time.delta;
                }else speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
            }
            shieldAlpha -= delta() / 15f;
            if(shieldAlpha < 0) shieldAlpha = 0f;

            heat -= delta();
            if(heat <= 0f && shield < maxShield) shield += regenAmount * delta();
        }

        @Override
        protected void updateShooting(){
            if(this.health < maxHealth / 10){
                if(reload >= reload && !charging()){
                    BulletType type = peekAmmo();
                    shoot(type);
                    reload %= reload;
                }else{
                    reload += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
                }
            }else{
                super.updateShooting();
            }
        }

        @Override
        protected void shoot(BulletType b){
            super.shoot(b);
            if(this.health < maxHealth / 10){
                slowDownReload = slowReloadTime;
                if(speedupScl < maxReloadMultiplier){
                    speedupScl += speedupPerShot;
                }else speedupScl = maxReloadMultiplier;
            }else{
                super.shoot(b);
            }
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, team.color);
            if(minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.health);
        }

        //region armor zone
        //region primary armor (active)
        @Override
        public void created(){
            super.created();
            shield = maxShield = health;
        }

        @Override
        public void damage(float amount){
            rawDamage(Math.max(amount - primaryArmor, minArmorDamage * amount));
        }

        @Override
        public void damagePierce(float amount, boolean withEffect){
            float pre = hitTime;

            rawDamage(amount);

            if(!withEffect) hitTime = pre;
        }

        protected void rawDamage(float amount){
            boolean hadShields = shield > 0.0001f;

            if(hadShields) shieldAlpha = 1f;
            heat = regenCooldown;

            float shieldDamage = Math.min(Math.max(shield, 0), amount);
            shield -= shieldDamage;
            hitTime = 1f;
            amount -= shieldDamage;

            if(amount > 0){
                health -= amount;
                if(health <= 0 && !dead()) kill();

                if(hadShields && shield <= 0.0001f) SnFx.blockShieldBreak.at(x, y, 0, this);
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(shield);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            shield = read.f();
        }

        //endregion primary armor (active)
        //region secondary armor (passive)
        @Override
        public float handleDamage(float amount){
            if(secondaryArmor != 0 | this.health < maxHealth / 10){
                return Math.max(amount - secondaryArmor, minArmorDamage * amount);
            }else return Math.max(amount - secondaryArmor / 10, minArmorDamage * amount);
        }

        //TODO make a visual display of the secondary armor
        //endregion secondary armor (passive)
        //endregion armor zone
    }
}
