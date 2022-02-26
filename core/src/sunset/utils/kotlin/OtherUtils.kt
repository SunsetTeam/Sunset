package sunset.utils.kotlin

import arc.graphics.Color
import arc.struct.*

//Map set region
operator fun <K, V> ArrayMap<K, V>.set(key: K, value: V?) = put(key, value)

operator fun <K, V> ObjectMap<K, V>.set(key: K, value: V?) = put(key, value)
operator fun <K, V> OrderedMap<K, V>.set(key: K, value: V?) = put(key, value)
operator fun <V> IntMap<V>.set(key: Number, value: V?) = put(key.toInt(), value)


operator fun <K> ObjectFloatMap<K>.set(key: K, value: Float) = put(key, value)
operator fun <K> ObjectIntMap<K>.set(key: K, value: Int) = put(key, value)


operator fun <V> GridMap<V>.set(x: Number, y: Number, value: V?) = put(x.toInt(), y.toInt(), value)


operator fun IntFloatMap.set(key: Int, value: Float) = put(key, value)

operator fun IntIntMap.set(key: Int, value: Int) = put(key, value)




inline fun String.color()= Color.valueOf(this)!!
inline fun Int.color()= Color(this)