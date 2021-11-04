package sunset.world.blocks.environment;

import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.world.blocks.environment.Floor;

public class Geyser extends Floor {
    public Effect calmEffect = Fx.none;
    public Effect steamEffect = Fx.none;
    public Effect eruptionEffect = Fx.none;
    public float steamDamage = 0.5f;
    public float eruptionDamage = 1f;

    public Geyser(String name) {
        super(name);
        Class<? extends Geyser> aClass = getClass();
        if (aClass.isAnonymousClass() && aClass.getSuperclass() == Geyser.class || aClass == Geyser.class) {
//            return;
        }
//        throw new IllegalAccessException("You cannot extends");
    }

}
