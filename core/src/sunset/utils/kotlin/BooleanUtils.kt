@file:JvmName("BooleanUtils")
package sunset.utils.kotlin


//and
operator fun Boolean.times(other: Boolean): Boolean = this and other;

//or
operator fun Boolean.plus(other: Boolean): Boolean = this or other;
