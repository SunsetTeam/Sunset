package sunset.type.unitTypes;

import arc.struct.Seq;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.UnitEntity;

public class ParticleUnitType extends UnitTypeExt{
    public Seq<ParticleEffect> particleEffects = new Seq<>();

    public ParticleUnitType(String name){
        super(name);

        lowAltitude = true;
        constructor = UnitEntity::create;
    }
}
