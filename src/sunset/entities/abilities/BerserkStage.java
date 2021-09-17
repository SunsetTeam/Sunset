package sunset.entities.abilities;

import mindustry.content.StatusEffects;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class BerserkStage {
    /** Максимально возможный процент прочности, при котором данный этап будет активен. */
    public float healthMaximum;
    /** Порядковый индекс. Чем ниже, тем меньше по здоровью. Не устанавливать вручную! */
    public int index;
    /** Эффект, накладываемый данным этапом. */
    public StatusEffect effect = StatusEffects.none;
    public float bulletWidthMultiplier = 1f;

    /** Переопределяйте для обработки логики данного этапа. */
    public void update(Unit unit) { }
    /** Переопределяйте для отрисовки данного этапа. */
    public void draw(Unit unit) { }
}
