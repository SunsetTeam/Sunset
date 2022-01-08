@file:JvmName("VectorUtils")

package tests.kotlin.utils

import arc.math.geom.Vec2
import arc.math.geom.Vec3
import arc.math.geom.Vector

operator fun <T : Vector<T>> T.plusAssign(other: T) {
    add(other)
}

operator fun <T : Vector<T>> T.minusAssign(other: T) {
    sub(other)
}

operator fun <T : Vector<T>> T.times(scl: Number): T {
    return cpy().scl(scl.toFloat())
}

operator fun <T : Vector<T>> T.timesAssign(scl: Number) {
    scl(scl.toFloat())
}

operator fun <T : Vector<T>> T.timesAssign(other: T) {
    scl(other)
}

operator fun <T : Vector<T>> T.divAssign(other: T) {
    div(other)
}


operator fun Number.div(vector: Vec2): Vec2 {
    return Vec2(toFloat() / vector.x, toFloat() / vector.y)
}

operator fun Number.div(vector: Vec3): Vec3 {
    return Vec3(toFloat() / vector.x, toFloat() / vector.y, toFloat() / vector.z)
}

operator fun Vec2.rem(other: Vec2): Vec2 {
    return Vec2(x % other.x, y % other.y)
}

operator fun Vec2.rem(number: Number): Vec2 {
    return Vec2(x % number.toFloat(), y % number.toFloat())
}

operator fun Vec3.rem(other: Vec2): Vec3 {
    return Vec3(x % other.x, y % other.y, z)
}

operator fun Vec3.rem(other: Vec3): Vec3 {
    return Vec3(x % other.x, y % other.y, z % other.z)
}

operator fun Vec3.rem(number: Number): Vec3 {
    return Vec3(x % number.toFloat(), y % number.toFloat(), z % number.toFloat())
}



operator fun Vec2.remAssign(other: Vec2) {
    x%=other.x;
    y%=other.y;
}

operator fun Vec2.remAssign(number: Number) {
    x%=number.toFloat();
    y%=number.toFloat();
}

operator fun Vec3.remAssign(other: Vec2) {
    x%=other.x;
    y%=other.y;
}

operator fun Vec3.remAssign(other: Vec3) {
    x%=other.x;
    y%=other.y;
    z%=other.z;
}

operator fun Vec3.remAssign(number: Number) {
    x%=number.toFloat();
    y%=number.toFloat();
    z%=number.toFloat();
}

/*

operator fun <T : Vector<T>> Vector<T>.minusAssign(other: Vector<T>) {
    sub(other as T)
}*/
fun main() {

    val vec: Vec2 = Vec2(1f, 2f) / Vec2(1f, 3f);
    vec *= 10;
}
