package sunset.type;

import arc.Core;
import arc.func.Func;
import arc.graphics.Pixmap;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mma.type.ImageGenerator;
import mma.type.pixmap.PixmapProcessor;
import sunset.utils.PixmapRotator;

public class CopterUnitType extends SnUnitType implements ImageGenerator {
    public final Seq<Rotor> rotors = new Seq<>();
    public float unitFallRotateSpeed = 6f;

    public CopterUnitType(String name) {
        super(name);

        constructor = UnitEntity::create;
        flying = lowAltitude = true;
        fallSpeed = 0.005f;
        engineSize = 0f;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if (unit.health <= 0 || unit.dead()) {
            unit.rotation += Time.delta * (fallSpeed * 2000);
            unit.rotation = Time.time * unitFallRotateSpeed;
        }
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        drawRotor(unit);
    }

    public void drawRotor(Unit unit) {
        applyColor(unit);
        rotors.each(rotor -> rotor.draw(unit));
        Draw.reset();
    }

    @Override
    public void load() {
        super.load();
        rotors.each(Rotor::load);
        shadowRegion = Core.atlas.find(name + "-no-rotors", shadowRegion);
    }

    @Override
    public Pixmap generate(Pixmap icon, PixmapProcessor processor) {
        processor.save(icon,name+"-no-rotors");
        for (Rotor rotor : rotors) {
            Pixmap rotreg = PixmapProcessor.outline(processor.get(rotor.rotorRegion));
//            Pixmap top = processor.get(rotor.topRegion).outline(Pal.darkerMetal,3);
            Pixmap top =processor.get(rotor.topRegion);
//            processor.save(rotreg, rotor.name + "-outline");
            processor.replace(rotor.rotorRegion,PixmapProcessor.outline(top));
            rotreg =PixmapProcessor.outline( fullRotor(rotreg,processor,rotor.rotorCount));
            processor.replace(rotor.topRegion,top);
            int xoffset = (int) (rotor.offsetX / Draw.scl + icon.width / 2f);
            int yoffset = (int) (-rotor.offsetY / Draw.scl + icon.height / 2f);
            int rx = xoffset - rotreg.width / 2;
            int ry = yoffset - rotreg.height / 2;
            int tx = xoffset - top.width / 2;
            int ty = yoffset - top.height / 2;
//            icon = processor.drawScaleAt(icon, rotreg, rx, ry);
            icon.draw(rotreg,rx,ry,true);
//            xoffset = (int) (rotor.offsetX / Draw.scl + icon.width / 2f);
//            yoffset = (int) (-rotor.offsetY / Draw.scl + icon.height / 2f);
            icon.draw(top,tx,ty,true);
//            icon = processor.drawScaleAt(icon, top, tx, ty);
        }
        return icon;
    }
    private Pixmap fullRotor(Pixmap rotreg,PixmapProcessor processor,int count){
        int size = Math.max(rotreg.width, rotreg.height);
        Pixmap fullreg = new Pixmap(size, size);
        for (int i = 0; i <count; i++) {
            PixmapProcessor.drawCenter(fullreg, PixmapRotator.rotate(rotreg, i/(float)count*360f));
        }
        return fullreg;
    }

}