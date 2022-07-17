/**
 * Allows you to store information about a specific unit, usually for AI work.
 */
@file:JvmName("UnitDataKt")
@file:JvmMultifileClass

package sunset.utils

import arc.Events
import arc.func.Cons2
import arc.func.Cons3
import arc.func.Prov
import arc.struct.IntMap
import arc.struct.ObjectMap
import arc.struct.ObjectSet
import arc.util.Log
import arc.util.io.Reads
import arc.util.io.ReusableByteOutStream
import arc.util.io.Writes
import mindustry.game.EventType
import mindustry.game.EventType.UnitDestroyEvent
import mindustry.game.EventType.WorldLoadEvent
import mindustry.gen.Groups
import mindustry.gen.Unit
import mindustry.io.SaveFileReader
import sunset.gen.UnitDataKeySerializer
import sunset.utils.kotlin.set
import java.io.DataInput
import java.io.DataOutput
import java.io.DataOutputStream


object UnitData {
    internal val data = IntMap<IntMap<Any>>()//ObjectMap<Unit,ObjectMap<DataKey,Object>>


    @JvmStatic
    fun <T> dataKey(def: Prov<T?>?): DataKey<T> {
        return DataKey(def)
    }

    @JvmStatic
    fun <T> getData(unit: Unit?, key: DataKey<*>, def: Prov<T?>): T? {
        if (unit == null || invalidUnit(unit)) return null;
        @Suppress("UNCHECKED_CAST")
        return (data[unit.id, { IntMap() }] as IntMap<T>)[key.id, def]
    }

    @JvmStatic
    fun <T> setData(unit: Unit?, key: DataKey<T>, value: T?) {
        if (unit == null || invalidUnit(unit)) return
        data[unit.id, { IntMap() }][key.id] = value
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
        Events.on(WorldLoadEvent::class.java) { data.clear() }
        Events.on(UnitDestroyEvent::class.java) { e: UnitDestroyEvent -> data.remove(e.unit.id) }
    }

    fun validUnit(unit: Unit?): Boolean {
        return !invalidUnit(unit)
    }

    fun invalidUnit(unit: Unit?): Boolean {
        return unit == null || !unit.isValid || unit.isNull || Groups.unit.getByID(unit.id) == null
    }

    private var dataKeysAmount = 0

    internal val namedDataKeys = ObjectSet<DataKey<Any?>>()

    class DataKey<T> internal constructor(def: Prov<T?>?) {
        internal val id: Int = dataKeysAmount++
        private val def: Prov<T?> = def ?: Prov { null }
        var name: String? = null
            private set
        var version: Int? = null
            private set
        var writer: Cons3<Writes, Unit, T?>? = null
            private set
        var reader: ((Reads) -> T?)? = null
            private set

        operator fun get(unit: Unit?): T? {
            return getData(unit, this, def)
        }

        operator fun set(unit: Unit?, value: T?) {
            setData(unit, this, value)
        }

        /**
         * @param reader used in annotation processing
         * */
        fun shouldSave(saveName: String, version: Int, writer: Cons3<Writes, Unit, T?>, @Suppress("UNUSED_PARAMETER") reader: (Reads, Unit) -> T?): DataKey<T> {
//            @Suppress("UNCHECKED_CAST")
//            namedDataKeys.add(this as DataKey<Any?>)
            this.name = saveName;
            this.version = version;
            this.writer = writer;
//            this.reader = reader;
            @Suppress("UNCHECKED_CAST")
            namedDataKeys.add(this as DataKey<Any?>)
//            TODO("add code for UnitData.DataKey.shouldSave")
            return this;
        }

    }
}
//TODO not completed
private object UnitDataWorldChunk : SaveFileReader.CustomChunk {
    override fun write(stream: DataOutput) {
        val data = UnitData.data;
        stream.write(0)//revision

        stream.write(data.size)
        val namedDataKeys = IntMap<UnitData.DataKey<Any?>>().also { UnitData.namedDataKeys.toSeq().each { key -> it.put(key.id, key) } }
        for ((unitId, unitDataKeys) in data) {
            val unit = Groups.unit.getByID(unitId)
            val write = Writes.get(stream)

            val dataKeysToSave = unitDataKeys.keys().toArray().toArray().filter { namedDataKeys[it].name != null }
            write.i(dataKeysToSave.size)
            if (dataKeysToSave.isEmpty()) continue

            writeUnit(write, unit)

            for (dataKey in dataKeysToSave.map { namedDataKeys[it] }) {
                val dataValue = unitDataKeys[dataKey.id]

                write.str(dataKey.name)
                write.i(dataKey.version ?: Int.MIN_VALUE)
                val dout = ReusableByteOutStream()

                dataKey.writer?.get(Writes.get(DataOutputStream(dout)),unit, dataValue)

                write.i(dout.size())
                write.b(dout.bytes)
            }
        }
//        TODO("Not yet implemented")
    }

    override fun read(stream: DataInput) {
        val data = UnitData.data;
        val revision = stream.readInt()

        val size = stream.readInt()
        if (size == 0) return


        val namedDataKeys = ObjectMap<String, UnitData.DataKey<Any?>>().also { UnitData.namedDataKeys.toSeq().each { key -> it.put(key.name, key) } }

        repeat(size) { _ ->
//            val unit = Groups.unit.getByID(unitId)
            val read = Reads.get(stream)

            val savedDataKeysAmount = read.i()

            if (savedDataKeysAmount == 0) return@repeat

            //region unit saving
            val unit = readUnit(read)
            //endregion
            repeat(savedDataKeysAmount)  dataKeyReadLoop@ {
                val dataKeyName = read.str()
                val dataKeyVersion = read.i()

                val dataKey = namedDataKeys[dataKeyName]
                val reader = UnitDataKeySerializer.getReader<Any?>(dataKeyName, dataKeyVersion)

                val saveSize = read.i()
                if (reader == null || dataKey == null) {
                    stream.skipBytes(saveSize)
                    Log.err("Cannot find reader or dataKey with name \"@\" and version \"@\"", dataKeyName, dataKeyVersion)
                    return@dataKeyReadLoop
                }
                if (unit == null) {
                    return@dataKeyReadLoop
                }
                data[unit.id, { IntMap() }][dataKey.id] = reader[read,unit]
            }
        }
//        TODO("Not yet implemented")
    }

    private fun readUnit(read: Reads): Unit? {
        /*val unitElevation = read.f()
        val unitHealth = read.f()
        val unitRotation = read.f()
        val unitTeam = TypeIO.readTeam(read)
        val unitTypeName = TypeIO.readString(read)
        val unitVel = TypeIO.readVec2(read)
        val unitX = read.f()
        val unitY = read.f()

        val unit = Groups.unit.find { unit ->
            unit.elevation == unitElevation &&
                    unit.health == unitHealth &&
                    unit.rotation == unitRotation &&
                    unit.team == unitTeam &&
                    unit.type?.name == unitTypeName &&
                    unit.vel == unitVel &&
                    unit.x == unitX &&
                    unit.y == unitY
        }*/
        val unitId = read.i();
        return Groups.unit.getByID(unitId)
    }

    private fun writeUnit(write: Writes, unit: Unit) {
        write.i(unit.id)
        /*write.f(unit.elevation)
        write.f(unit.health)
        write.f(unit.rotation)
        TypeIO.writeTeam(write, unit.team)
        TypeIO.writeString(write, unit.type?.name)
        TypeIO.writeVec2(write, unit.vel)
        write.f(unit.x)
        write.f(unit.y)*/
    }
}

private inline operator fun <reified V> IntMap.Entry<V>.component1(): Int = key
private inline operator fun <reified V> IntMap.Entry<V>.component2(): V = value
