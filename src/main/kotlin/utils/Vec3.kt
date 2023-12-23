package utils

import kotlin.math.sqrt

open class Vec3(
	x: Double,
	y: Double,
	z: Double,
) {
	constructor() : this(0.0, 0.0, 0.0)

	val elements = arrayOf(x, y, z)

	open val x: Double
		get() = elements[0]
	open val y: Double
		get() = elements[1]
	open val z: Double
		get() = elements[2]
	val unit: Vec3
		get() = this / length
	val length: Double
		get() = sqrt(length2)
	val length2: Double
		get() = x * x + y * y + z * z

	operator fun get(i: Int) = elements[i]
	operator fun unaryPlus() = Vec3(+x, +y, +z)
	operator fun unaryMinus() = Vec3(-x, -y, -z)
	operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
	operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
	operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)
	operator fun times(d: Double) = Vec3(x * d, y * d, z * d)
	operator fun div(d: Double) = Vec3(x / d, y / d, z / d)
	infix fun dot(v: Vec3) = x * v.x + y * v.y + z * v.z
	infix fun cross(v: Vec3) = Vec3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)
}
