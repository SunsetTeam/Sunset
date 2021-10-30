package sunset.type;

import arc.Events;
import arc.func.Prov;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.game.EventType.UnitDestroyEvent;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

/**
 * Позволяет хранить информацию о конкретном юните, как правило для работы ИИ.
 */
public class UnitData {
    private static ObjectMap<Unit, ObjectMap<String, Object>> data = new ObjectMap<>();

    public static <T> T data(Unit unit, String key, Prov<T> prov) {
        if (unit != null && !validUnit(unit)) return null;
        ObjectMap<String, Object> entries = data.get(unit, ObjectMap::new);
        return (T) entries.get(key, prov::get);
    }

    public static <T> void data(Unit unit, String key, T value) {
        if (unit == null || unit.dead) return;
        data.get(unit, ObjectMap::new).put(key, value);
    }

    public static void init() {
        /*Events.on(EventType.SaveWriteEvent.class, e -> {
            Map map = Vars.state.map;
            if (map != null) {
                map.tags.put(fullName("unit-data"),data);
            }
        });
        Events.on(EventType.SaveLoadEvent.class, e -> {
            Map map = Vars.state.map;
            if (map != null) {
                Log.info("tags: @", map.tags);
            } else {
                Log.info("no tags");
            }
        });*/
        Events.run(EventType.Trigger.update, () -> {
            Seq<Unit> select = data.keys().toSeq().select(UnitData::validUnit
            );
            for (Unit unit : select) {
                data.remove(unit);
            }
        });
        Events.on(EventType.WorldLoadEvent.class, e -> data.clear());
        Events.on(UnitDestroyEvent.class, (e) -> data.remove(e.unit));
    }

    private static boolean validUnit(Unit unit) {
        return !unit.isValid() || unit.isNull() ||
                Groups.unit.getByID(unit.id) == null;
    }
}