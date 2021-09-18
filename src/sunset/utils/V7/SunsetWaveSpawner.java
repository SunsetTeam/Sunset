package sunset.utils.V7;

import arc.Events;
import arc.func.Floatc2;
import arc.func.Intc2;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.ai.WaveSpawner;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.entities.Damage;
import mindustry.game.EventType.WorldLoadEvent;
import mindustry.gen.Building;
import mindustry.world.Tile;

import static mindustry.Vars.*;

public class SunsetWaveSpawner extends WaveSpawner{
    private static final float margin = 0f, coreMargin = tilesize * 2f, maxSteps = 30;

    private int tmpCount;
    private Seq<Tile> spawns = new Seq<>();
    private boolean spawning = false;
    private boolean any = false;
    private Tile firstSpawn = null;

    public SunsetWaveSpawner(){
        Events.on(WorldLoadEvent.class, e -> reset());
    }

    @Override
    @Nullable
    public Tile getFirstSpawn(){
        firstSpawn = null;
        eachGroundSpawn((cx, cy) -> {
            firstSpawn = world.tile(cx, cy);
        });
        return firstSpawn;
    }

    @Override
    public int countSpawns(){
        return spawns.size;
    }

    @Override
    public Seq<Tile> getSpawns(){
        return spawns;
    }

    /** @return true if the player is near a ground spawn point. */
    @Override
    public boolean playerNear(){
        return state.hasSpawns() && !player.dead() && spawns.contains(g -> Mathf.dst(g.x * tilesize, g.y * tilesize, player.x, player.y) < state.rules.dropZoneRadius && player.team() != state.rules.waveTeam);
    }

    @Override
    public void doShockwave(float x, float y){
        Fx.spawnShockwave.at(x, y, state.rules.dropZoneRadius);
        Damage.damage(state.rules.waveTeam, x, y, state.rules.dropZoneRadius, 99999999f, true);
    }

    @Override
    public void eachGroundSpawn(Intc2 cons){
        eachGroundSpawn(-1, (x, y, shock) -> cons.get(World.toTile(x), World.toTile(y)));
    }

    private void eachGroundSpawn(int filterPos, SpawnConsumer cons){
        if(state.hasSpawns()){
            for(Tile spawn : spawns){
                if(filterPos != -1 && filterPos != spawn.pos()) continue;

                cons.accept(spawn.worldx(), spawn.worldy(), true);
            }
        }

        if(state.rules.attackMode && state.teams.isActive(state.rules.waveTeam) && !state.teams.playerCores().isEmpty()){
            Building firstCore = state.teams.playerCores().first();
            for(Building core : state.rules.waveTeam.cores()){
                if(filterPos != -1 && filterPos != core.pos()) continue;

                Tmp.v1.set(firstCore).sub(core).limit(coreMargin + core.block.size * tilesize /2f * Mathf.sqrt2);

                boolean valid = false;
                int steps = 0;

                //keep moving forward until the max step amount is reached
                while(steps++ < maxSteps){
                    int tx = World.toTile(core.x + Tmp.v1.x), ty = World.toTile(core.y + Tmp.v1.y);
                    any = false;
                    Geometry.circle(tx, ty, world.width(), world.height(), 3, (x, y) -> {
                        if(world.solid(x, y)){
                            any = true;
                        }
                    });

                    //nothing is in the way, spawn it
                    if(!any){
                        valid = true;
                        break;
                    }else{
                        //make the vector longer
                        Tmp.v1.setLength(Tmp.v1.len() + tilesize*1.1f);
                    }
                }

                if(valid){
                    cons.accept(core.x + Tmp.v1.x, core.y + Tmp.v1.y, false);
                }
            }
        }
    }

    private void eachFlyerSpawn(int filterPos, Floatc2 cons){
        for(Tile tile : spawns){
            if(filterPos != -1 && filterPos != tile.pos()) continue;

            float angle = Angles.angle(world.width() / 2f, world.height() / 2f, tile.x, tile.y);
            float trns = Math.max(world.width(), world.height()) * Mathf.sqrt2 * tilesize;
            float spawnX = Mathf.clamp(world.width() * tilesize / 2f + Angles.trnsx(angle, trns), -margin, world.width() * tilesize + margin);
            float spawnY = Mathf.clamp(world.height() * tilesize / 2f + Angles.trnsy(angle, trns), -margin, world.height() * tilesize + margin);
            cons.get(spawnX, spawnY);
        }

        if(state.rules.attackMode && state.teams.isActive(state.rules.waveTeam)){
            for(Building core : state.rules.waveTeam.data().cores){
                if(filterPos != -1 && filterPos != core.pos()) continue;

                cons.get(core.x, core.y);
            }
        }
    }


    public int countGroundSpawns(){
        tmpCount = 0;
        eachGroundSpawn((x, y) -> tmpCount ++);
        return tmpCount;
    }

    public int countFlyerSpawns(){
        tmpCount = 0;
        eachFlyerSpawn(-1, (x, y) -> tmpCount ++);
        return tmpCount;
    }

    @Override
    public boolean isSpawning(){
        return spawning && !net.client();
    }

    private void reset(){
        spawning = false;
        spawns.clear();

        for(Tile tile : world.tiles){
            if(tile.overlay() == Blocks.spawn){
                spawns.add(tile);
            }
        }
    }

    private interface SpawnConsumer{
        void accept(float x, float y, boolean shockwave);
    }
}
