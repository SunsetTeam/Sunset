package sunset.type.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

public class Energy extends Ability {
    public final Seq<Energy> energy = new Seq<>();
    public float layer = Layer.bullet - 0.001f;
    public float x, y, radius = 5;
    public Color color = Pal.heal;

    public Energy() {}

    public void energy(Energy... energy) {
        this.energy.set(energy);
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        Draw.z(layer);
        Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        Fill.circle(rx, ry, radius);
        Draw.color();
        Fill.circle(rx, ry, radius / 2);
    }
}
