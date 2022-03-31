package sunset.type.unitTypes;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

public class ParticleUnitType extends UnitTypeExt{
    public boolean drawHover = true;
    public Color hoverBloomColor = Pal.engine;
    public Seq<ParticleEffect> particleEffects = new Seq<>();

    public TextureRegion hoverRegion;

    public void load() {
        super.load();
        hoverRegion = Core.atlas.find(name + "-hover");
    }

    public ParticleUnitType(String name){
        super(name);

        lowAltitude = true;
        constructor = UnitEntity::create;
    }
}
