package sunset.type;

import arc.struct.Seq;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import sunset.entities.abilities.BerserkAbility;

public class BerserkUnitType extends UnitType {
    public Seq<BerserkAbility> dmg = new Seq<>();

    public BerserkUnitType(String name) {
        super(name);
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if(dmg != null){
            for (int i = 0; i < dmg.size; i++) {
                StatusEffect se = new StatusEffect("se");
                se.damageMultiplier = dmg.get(i).damageMultiplier;
                se.speedMultiplier = dmg.get(i).speedMultiplier;
                if (health < dmg.get(i).needHealth) {
                    unit.apply(se);
                }
            }
        }
    }
    public void addRageMode(BerserkAbility... berserkAbilities){
        this.dmg = Seq.with(berserkAbilities);
    }
}
