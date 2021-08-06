package sunset.type;

import arc.Events;
import arc.struct.ObjectMap;
import mindustry.game.EventType.UnitDestroyEvent;
import mindustry.gen.Unit;

/** Позволяет хранить информацию о конкретном юните, как правило для работы ИИ. */
public class UnitData {
    private static ObjectMap<Unit, ObjectMap<String, Object>> data;
    public static <T> T data(Unit unit, String key) {
        if(unit == null || unit.dead) return null;
        ObjectMap<String, Object> unitdata = data.get(unit);
        if(unitdata == null) {
            data.put(unit, new ObjectMap<>());
            return null;
        }
        return (T)(unitdata.get(key));
    }
    public static <T> void data(Unit unit, String key, T value) {
        if(unit == null || unit.dead) return;
        ObjectMap<String, Object> unitdata = data.get(unit);
        if(unitdata == null) {
            unitdata = new ObjectMap<>();
            data.put(unit, unitdata);
        }
        unitdata.put(key, value);
    }
    public static void init() {
        data = new ObjectMap<>();
        Events.on(UnitDestroyEvent.class, (e) -> {
            data.remove(e.unit);
        });
    }
}