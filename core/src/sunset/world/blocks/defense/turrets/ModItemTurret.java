package sunset.world.blocks.defense.turrets;

import acontent.world.meta.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mma.graphics.*;
import sunset.*;
import sunset.content.affilitiation.*;
import sunset.utils.*;
import sunset.world.meta.*;

import static mindustry.Vars.tilesize;

/**
 * Item turret with useful things.
 * Features:<p>
 * 1) reload bar<p>
 * 2) power shot<p>
 * 3) guilds
 */
public class ModItemTurret extends ItemTurret{
    public boolean drawLight;

    public BulletType powerBullet;
    public boolean debug = false;
    public int chargeShots;
    public boolean reloadBar = true;

    public SnGuilds guild = SnGuilds.none;
    public SnSubGuilds subGuild = SnSubGuilds.none;
    public SnBranches branch = SnBranches.none;
    public AStats aStats = new AStats();

    public ModItemTurret(String name){
        super(name);
        drawLight = false;
        stats = aStats.copy(stats);
    }

    @Override
    public void setStats(){
        super.setStats();
        if(guild != SnGuilds.none) aStats.add(SnStat.guild, SnStatValues.affil(guild));
        if(subGuild != SnSubGuilds.none) aStats.add(SnStat.subGuild, SnStatValues.affil(subGuild));
        if(branch != SnBranches.none) aStats.add(SnStat.branch, SnStatValues.affil(branch));
    }

    @Override
    public void setBars(){
        super.setBars();
        if(reloadBar){
            SnVars.settings.registerReloadBarBlock(this, (ItemTurretBuild entity) -> new Bar(
            () -> Core.bundle.format("bar.sunset-reload", Utils.stringsFixed(Mathf.clamp(entity.reloadCounter / reload) * 100f)),
            () -> entity.team.color,
            () -> Mathf.clamp(entity.reloadCounter / reload)
            ));
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
        if(minRange > 0) Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, minRange, Pal.health);
    }

    public class ModItemTurretBuild extends ItemTurretBuild{
        boolean isShoot = false;
        int totalShoots = 0;

        @Override
        public void draw(){
            super.draw();

            if(debug) ADrawf.drawText(this, "Total Shoots: " + totalShoots);
        }

        @Override
        protected void updateShooting(){
            if(powerBullet != null){
                boolean canShoot = reload + delta() * peekAmmo().reloadMultiplier * baseReloadSpeed() >= reload && !charging();
                this.isShoot = canShoot;
                super.updateShooting();
                if(!canShoot || isShoot) return;
                totalShoots++;
                if(totalShoots == chargeShots){
                    totalShoots = 0;
                }
            }else{
                super.updateShooting();
            }
        }

        @Override
        protected void shoot(BulletType type){
            if(powerBullet != null) isShoot = false;
            super.shoot(type);
        }

        @Override
        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover){
            if(powerBullet != null && totalShoots == 0){
                super.bullet(powerBullet, xOffset, yOffset, angleOffset, mover);
            }else{
                super.bullet(type, xOffset, yOffset, angleOffset, mover);
            }
        }
    }
}
