package sunset.world.blocks.defense.walls;

import acontent.world.meta.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.*;
import sunset.world.meta.*;

public class SnWall extends Wall{
    private final AStats snStats = new AStats();
    /** Additional penalty to penetration. */
    public int pierceMultiplier = 0;
    /** Bullet damage multiplier. */
    public float collidedDamageMultiplier = 1f;

//    public boolean

    public float healAmount = 0.0f;
    public boolean canHeal = false;

    public SnWall(String name){
        super(name);
        stats = snStats.copy(stats);
        update = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        if(canHeal){
            stats.add(Stat.repairTime, health / healAmount / Time.toSeconds, StatUnit.seconds);
        }
        if(pierceMultiplier > 0) snStats.add(SnStat.pierceMultiplier, pierceMultiplier);
        if(collidedDamageMultiplier < 1) snStats.add(SnStat.pierceDamageDebuff, (1f - collidedDamageMultiplier) * 100, StatUnit.percent);
    }

    protected void healAmount(float healAmount){
        canHeal = true;
        this.healAmount = healAmount;
    }

    public class SnWallBuild extends WallBuild{

        @Override
        public void updateTile(){
            super.updateTile();
            if (canHeal){
                heal(healAmount * delta());
            }
        }

        @Override
        public boolean collision(Bullet bullet){
            super.collision(bullet);
            if(bullet.team == team) return false;
            if(bullet.type.pierceCap != -1 && bullet.type.pierce){
                for(int i = 0; i < pierceMultiplier; i++) bullet.collided.add(id);
            }
            bullet.damage *= collidedDamageMultiplier;
            return true;
        }
    }
}
