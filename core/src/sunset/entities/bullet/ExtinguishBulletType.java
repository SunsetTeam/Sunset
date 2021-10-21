package sunset.entities.bullet;

import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.entities.Units;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.gen.Bullet;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;

public class ExtinguishBulletType extends LiquidBulletType {
    public ExtinguishBulletType(Liquid liquid) {
        super(liquid);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        final boolean[] did = {false};
        if(liquid.canExtinguish()) {
            Units.nearby(b.team, b.x, b.y, hitSize, hitShake, unit -> {
                if(!did[0]) {
                    for (Content s : Vars.content.getBy(ContentType.status)) {
                        StatusEffect e = (StatusEffect)s;
                        // Считаем, что эффект - горение, если он наносит урон и
                        // среди его противоположностей есть вода. Такой подход позволит
                        // тушить "пожары" из других модов.
                        if(unit.hasEffect(e) && e.damage > 0 && e.opposites.contains(StatusEffects.wet)) {
                            unit.unapply(e);
                            Fx.steam.at(b.x, b.y);
                            did[0] = true;
                        }
                    }
                }
            });
        }
        if(did[0]) b.remove();
    }
}
