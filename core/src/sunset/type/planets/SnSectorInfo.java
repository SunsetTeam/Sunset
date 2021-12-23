package sunset.type.planets;

import arc.math.*;
import mindustry.game.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import sunset.game.*;

import static mindustry.Vars.state;

public class SnSectorInfo extends SectorInfo{
    @Override
    public void prepare(){
        super.prepare();
        waveVersion=SnWaves.waveVersion;
    }

    @Override
    public void write(){
        //enable attack mode when there's a core.
        if(state.rules.waveTeam.core() != null){
            attack = true;
            winWave = 0;
        }

        //if there are infinite waves and no win wave, add a win wave.
        if(winWave <= 0 && !attack){
            winWave = 30;
        }

        state.wave = wave;
        state.rules.waves = waves;
        state.rules.waveSpacing = waveSpacing;
        state.rules.winWave = winWave;
        state.rules.attackMode = attack;

        //assign new wave patterns when the version changes
        if(waveVersion != SnWaves.waveVersion && state.rules.sector.preset == null){
            state.rules.spawns = SnWaves.generate(state.rules.sector.threat);
        }

        CoreBuild entity = state.rules.defaultTeam.core();
        if(entity != null){
            entity.items.clear();
            entity.items.add(items);
            //ensure capacity.
            entity.items.each((i, a) -> entity.items.set(i, Mathf.clamp(a, 0, entity.storageCapacity)));
        }
    }

}
