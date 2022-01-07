@file:JvmName("ColorUtils")

package tests.kotlin.utils

import arc.graphics.Color

operator fun Color.set(range: IntRange, value: Number) {
    for (index in range) {
        this[index] = value;
    }
}

operator fun Color.set(index1: Int, index2: Number, value: Number) {
    this[index1] = value;
    this[index2] = value;
}

operator fun Color.set(index1: Int, index2: Number, index3: Number, value: Number) {
    this[index1, index2] = value;
    this[index3] = value;
}

operator fun Color.set(index1: Int, index2: Number, index3: Number, index4: Number, value: Number) {
    this[index1, index2, index3] = value;
    this[index4] = value;
}

operator fun Color.set(index: Number, value: Number) {
    when (index) {
        0 -> r = value.toFloat()
        1 -> g = value.toFloat()
        2 -> b = value.toFloat()
        3 -> a = value.toFloat()
        else -> {
            throw IndexOutOfBoundsException()
        }
    }
}

operator fun Color.get(index: Int): Float {
    return when (index) {
        0 -> r
        1 -> g
        2 -> b
        3 -> a
        else -> {
            throw IndexOutOfBoundsException()
        }
    }
}

operator fun Color.plusAssign(other: Color) {
    add(other);
}

operator fun Color.plus(other: Color): Color {
    return cpy().add(other);
}

operator fun Color.minusAssign(other: Color) {
    sub(other);
}

operator fun Color.minus(other: Color): Color {
    return cpy().sub(other);
}

operator fun Color.timesAssign(other: Color) {
    r *= other.r;
    g *= other.g;
    b *= other.b;
}

operator fun Color.timesAssign(i: Number) {
    r *= i.toFloat();
    g *= i.toFloat();
    b *= i.toFloat();
}

operator fun Number.times(color: Color): Color {
    val cpy = color.cpy()
    cpy.r = toFloat() * cpy.r;
    cpy.g = toFloat() * cpy.g;
    cpy.b = toFloat() * cpy.b;
    return cpy;
}

operator fun Number.timesAssign(color: Color) {
    color.r = toFloat() * color.r;
    color.g = toFloat() * color.g;
    color.b = toFloat() * color.b;
}

operator fun Color.times(i: Number): Color {
    val cpy = cpy()
    cpy *= i;
    return cpy;
}

operator fun Color.times(other: Color): Color {
    val cpy = cpy()
    cpy *= other;
    return cpy;
}

operator fun Color.divAssign(other: Color) {
    r /= other.r;
    g /= other.g;
    b /= other.b;
}

operator fun Color.divAssign(i: Number) {
    r /= i.toFloat();
    g /= i.toFloat();
    b /= i.toFloat();
}

operator fun Number.div(color: Color): Color {
    val cpy = color.cpy()
    cpy.r = toFloat() / cpy.r;
    cpy.g = toFloat() / cpy.g;
    cpy.b = toFloat() / cpy.b;
    return cpy;
}

operator fun Number.divAssign(color: Color) {
    color.r = toFloat() / color.r;
    color.g = toFloat() / color.g;
    color.b = toFloat() / color.b;
}

operator fun Color.div(i: Number): Color {
    val cpy = cpy()
    cpy /= i;
    return cpy;
}

operator fun Color.div(other: Color): Color {
    val cpy = cpy()
    cpy /= other;
    return cpy;
}

operator fun Color.rem(other: Color): Color {
    return Color(r % other.r, g % other.g, b % other.b)
}

operator fun Color.rem(number: Number): Color {
    return Color(r % number.toFloat(), g % number.toFloat(), b % number.toFloat())
}

operator fun Color.remAssign(other: Color) {
    r %= other.r;
    g %= other.g;
    b %= other.b;
}

operator fun Color.remAssign(number: Number) {
    r %= number.toFloat();
    g %= number.toFloat();
    b %= number.toFloat();
}