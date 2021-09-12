package sunset.type;

import arc.struct.Seq;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.meta.Stat;
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
            for (int i = 0; i > dmg.size; i++) {
                if (health < dmg.get(i).needHealth) {
                    stats.addPercent(Stat.damageMultiplier, dmg.get(i).damageMultiplier);
                    stats.addPercent(Stat.speedMultiplier, dmg.get(i).speedMultiplier);
                }
            }
        }
    }
    public void addRageMode(BerserkAbility... berserkAbilities){
        this.dmg = Seq.with(berserkAbilities);
    }
}
