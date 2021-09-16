package sunset.type;

import arc.struct.Seq;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import sunset.entities.abilities.BerserkAbility;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.gen.UnitEntity;

public class BerserkUnitType extends UnitType {
    public Seq<BerserkAbility> dmg = new Seq<>();

    public BerserkUnitType(String name) {
        super(name);
        constructor = LegsUnit::create;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if(dmg != null){
            for (int i = 0; i < dmg.size; i++) {
                if (health < dmg.get(i).needHealth) {
                }
            }
        }
    }
    public void addRageMode(BerserkAbility... berserkAbilities){
        this.dmg = Seq.with(berserkAbilities);
    }
}
