package utils

import kotlin.math.*
import kotlin.random.Random.Default.nextDouble

data class Vec3(
	val x: Double,
	val y: Double,
	val z: Double,
) {
	constructor() : this(0.0, 0.0, 0.0)

	val unit: Vec3
		get() = this / length
	val length: Double
		get() = sqrt(length2)
	val length2: Double
		get() = x * x + y * y + z * z

	operator fun get(i: Int) = when (i) {
		0    -> component1()
		1    -> component2()
		2    -> component3()
		else -> throw ArrayIndexOutOfBoundsException()
	}

	operator fun unaryPlus() = Vec3(+x, +y, +z)
	operator fun unaryMinus() = Vec3(-x, -y, -z)
	operator fun plus(v: Vec3) = Vec3(x + v.x, y + v.y, z + v.z)
	operator fun <T : Number> plus(n: T) = Vec3(x + n.toDouble(), y + n.toDouble(), z + n.toDouble())
	operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
	operator fun <T : Number> minus(n: T) = Vec3(x - n.toDouble(), y - n.toDouble(), z - n.toDouble())
	operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)
	operator fun <T : Number> times(n: T) = Vec3(x * n.toDouble(), y * n.toDouble(), z * n.toDouble())
	operator fun <T : Number> div(n: T) = Vec3(x / n.toDouble(), y / n.toDouble(), z / n.toDouble())
	infix fun dot(v: Vec3) = x * v.x + y * v.y + z * v.z
	infix fun cross(v: Vec3) = Vec3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)

	fun nearZero() = abs(x) < 1e-8 && abs(y) < 1e-8 && abs(z) < 1e-8
	fun reflect(normal: Vec3): Vec3 = this - 2 * (this dot normal) * normal
	fun refract(normal: Vec3, reflectionRatio: Double): Vec3 {
		val cos = (-this dot normal).coerceAtMost(1.0)
		val rayPerp = reflectionRatio * (this + cos * normal)
		val rayPara = -sqrt(abs(1.0 - rayPerp.length2)) * normal
		return rayPerp + rayPara
	}

	override fun toString(): String =
		"${javaClass.canonicalName}[$x $y $z]"

	companion object {
		fun random() = Vec3(nextDouble(), nextDouble(), nextDouble())
		fun random(from: Double, until: Double) =
			Vec3(nextDouble(from, until), nextDouble(from, until), nextDouble(from, until))

		fun randomInUnitSphere(): Vec3 {
			val radius = nextDouble()
			val theta = nextDouble(0.0, 2 * PI)
			val phi = nextDouble(-PI / 2, PI / 2)
			return Vec3(
				radius * cos(phi) * cos(theta),
				radius * cos(phi) * sin(theta),
				radius * sin(phi)
			)
		}

		fun randomUnitVector(): Vec3 {
			val theta = nextDouble(0.0, 2 * PI)
			val phi = nextDouble(-PI / 2, PI / 2)
			return Vec3(
				cos(phi) * cos(theta),
				cos(phi) * sin(theta),
				sin(phi)
			)
		}

		fun randomOnHemisphere(normal: Vec3) =
			randomUnitVector().let {
				if (it dot normal > 0.0) it else -it
			}
	}
}

operator fun <T : Number> T.times(v: Vec3) = v * this
