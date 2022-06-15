package sunset.entities.comp;

import arc.math.geom.*;
import arc.struct.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mma.annotations.ModAnnotations.*;
import sunset.gen.*;
import sunset.world.blocks.defense.turrets.ConcentratorTurret.*;

@Component()
@EntityDef(value = {ConcentratorBulletc.class}, pooled = true)
abstract class ConcentratorBulletComp implements Bulletc{
    public Team bornTeam;
    @Import
    Team team;
    @Import
    Entityc owner;
    @Import
    float x, y, damage, lastX, lastY, time, lifetime, aimX, aimY, originX, originY, fdata;
    @Import
    Vec2 vel;
    @Import
    IntSeq collided = new IntSeq(6);
    @Import
    BulletType type;
    @Import
    Object data;
    // setting this variable to true prevents lifetime from decreasing for a frame.
    @Import
    transient boolean keepAlive, absorbed, hit;
    @Import
    transient Tile aimTile;
    @Import
    transient Mover mover;
    @Import
    transient Trail trail;
    @Import
    private float rotation;

    public MagneticTurretBuild buildOwner(){
        return owner.as();
    }

    @Override
    @Replace
    public void update(){
        if(bornTeam != team || !owner.isAdded()){
            absorb();
            return;
        }
        time = 0;
        lifetime = Float.MAX_VALUE;
        originalUpdate();
    }

    @SuperMethod(parentName = "update")
    protected abstract void originalUpdate();
}
