package sunset.type;

import arc.Events;
import arc.func.Prov;
import arc.struct.IntMap;
import mindustry.game.EventType;
import mindustry.game.EventType.UnitDestroyEvent;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

/**
 * Позволяет хранить информацию о конкретном юните, как правило для работы ИИ.
 */
public class UnitData {

    private static IntMap<IntMap<Object>> data = new IntMap<>();

    public static DataKey dataKey() {
        return new DataKey();
    }

    public static <T> T getData(Unit unit, DataKey key, Prov<T> def) {
        if (unit == null || invalidUnit(unit)) return null;
//        return ;
        IntMap<Object> entries = data.get(unit.id, IntMap::new);
        if (entries.containsKey(key.id)) {
            return (T) entries.get(key.id);
        } else {
            return def.get();
        }
    }

    public static <T> void setData(Unit unit, DataKey key, T value) {
        if (unit == null || invalidUnit(unit)) return;
        data.get(unit.id, IntMap::new).put(key.id, value);
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
            IntMap.Keys keys = data.keys();
            while (keys.hasNext) {
                int id = keys.next();
                if (invalidUnit(Groups.unit.getByID(id))) data.remove(id);
            }
        });
        Events.on(EventType.WorldLoadEvent.class, e -> data.clear());
        Events.on(UnitDestroyEvent.class, (e) -> data.remove(e.unit.id));
    }

    public static boolean validUnit(Unit unit) {
        return !invalidUnit(unit);
    }

    public static boolean invalidUnit(Unit unit) {
        return !unit.isValid() || unit.isNull() || Groups.unit.getByID(unit.id) == null;
    }

    public static class DataKey {
        private static int totalId;
        private int id;

        private DataKey() {
            id = totalId++;
        }
    }
}