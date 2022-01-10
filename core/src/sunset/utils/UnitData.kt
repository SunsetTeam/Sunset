/**
 * Allows you to store information about a specific unit, usually for AI work.
 */
@file:JvmName("UnitData")
@file:JvmMultifileClass

package sunset.utils

import arc.Events
import arc.func.Prov
import arc.struct.IntMap
import mindustry.game.EventType
import mindustry.game.EventType.UnitDestroyEvent
import mindustry.game.EventType.WorldLoadEvent
import mindustry.gen.Groups
import mindustry.gen.Unit

object UnitData {
    @JvmStatic
    val data = IntMap<IntMap<Any>>()

    @JvmStatic
    fun <T> dataKey(def: Prov<T?>?): DataKey<T> {
        return DataKey(def)
    }

    @JvmStatic
    fun <T> getData(unit: Unit?, key: DataKey<*>, def: Prov<T?>): T? {
        if (unit == null || invalidUnit(unit)) return null;
        return ( data.get(unit.id, { IntMap() }) as IntMap<T>).get(key.id,def)
    }

    @JvmStatic
    fun <T> setData(unit: Unit?, key: DataKey<T>, value: T?) {
        if (unit == null || invalidUnit(unit)) return
        data[unit.id, { IntMap() }].put(key.id, value)
    }

    @JvmStatic
    fun init() {
        Events.run(EventType.Trigger.update) {
            val keys = data.keys()
            while (keys.hasNext) {
                val id = keys.next()
                if (invalidUnit(Groups.unit.getByID(id))) data.remove(id)
            }
        }
        Events.on(WorldLoadEvent::class.java) { e: WorldLoadEvent? -> data.clear() }
        Events.on(UnitDestroyEvent::class.java) { e: UnitDestroyEvent -> data.remove(e.unit.id) }
    }

    fun validUnit(unit: Unit?): Boolean {
        return !invalidUnit(unit)
    }

    fun invalidUnit(unit: Unit?): Boolean {
        return unit == null || !unit.isValid || unit.isNull || Groups.unit.getByID(unit.id) == null
    }

    class DataKey<T> internal constructor(def: Prov<T?>?) {
        internal val id: Int = totalId++
        private val def: Prov<T?> = def ?: Prov { null }

        operator fun get(unit: Unit?): T? {
            return getData(unit, this, def)
        }

        operator fun set(unit: Unit?, value: T?) {
            setData(unit, this, value)
        }

        companion object {
            private var totalId = 0
        }

    }
}

