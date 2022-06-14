package sunset.type.unitTypes;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.*;
import sunset.*;
import sunset.type.blocks.*;
import sunset.utils.*;

import static mindustry.Vars.*;

public class CopterUnitType extends SnUnitType{
    public final Seq<Rotor> rotors = new Seq<>();
    public float unitFallRotateSpeed = 6f;


    public CopterUnitType(String name){
        super(name);

        constructor = UnitEntity::create;
        flying = lowAltitude = true;
        fallSpeed = 0.005f;
        engineSize = 0f;
    }

    public void rotors(Rotor... rotors){
        this.rotors.set(rotors);
    }

    @Override
    public void update(Unit unit){
        super.update(unit);
        if(unit.health <= 0 || unit.dead()){
            unit.rotation += Time.delta * (fallSpeed * 2000);
            unit.rotation = Time.time * unitFallRotateSpeed;
            }
        }

    @Override
    public void draw(Unit unit){
        super.draw(unit);
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z);
        drawRotor(unit);
        Draw.reset();
    }

    public void drawRotor(Unit unit){
        applyColor(unit);
        rotors.each(rotor -> rotor.draw(unit));
        Draw.reset();
    }

    @Override
    public void drawShadow(Unit unit){
        if(!SnVars.settings.advancedShadows()){

            super.drawShadow(unit);
            return;
        }
        FrameBuffer buffer = renderer.effectBuffer;
        buffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
        buffer.begin(Color.clear);
        Tmp.c2.set(Draw.getColor());
        Draw.color(Color.black);

        Draw.rect(shadowRegion, unit.x, unit.y, unit.rotation - 90);
        drawRotor(unit);

        Draw.color(Tmp.c2);
        buffer.end();


        float e = Math.max(unit.elevation, shadowElevation) * (1f - unit.drownTime);
        float x = unit.x + shadowTX * e, y = unit.y + shadowTY * e;
        Floor floor = world.floorWorld(x, y);

        float dest = floor.canShadow ? 1f : 0f;
        //yes, this updates state in draw()... which isn't a problem, because I don't want it to be obvious anyway
        unit.shadowAlpha = unit.shadowAlpha < 0 ? dest : Mathf.approachDelta(unit.shadowAlpha, dest, 0.11f);
        Draw.color(Pal.shadow, Pal.shadow.a * unit.shadowAlpha);

        Draw.rect(Draw.wrap(buffer.getTexture()), Core.camera.position.x + shadowTX * e, Core.camera.position.y + shadowTY * e,
        Core.camera.width, -Core.camera.height);
        Draw.color();
    }

    @Override
    public void load(){
        super.load();
        rotors.each(Rotor::load);
        shadowRegion = Core.atlas.find(name + "-no-rotors", shadowRegion);
    }

}