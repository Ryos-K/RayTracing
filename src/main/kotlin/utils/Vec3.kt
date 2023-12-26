package utils

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sqrt
import kotlin.math.ulp
import kotlin.random.Random
import kotlin.random.Random.Default.nextDouble

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
	operator fun <T : Number> plus(n: T) = Vec3(x + n.toDouble(), y + n.toDouble(), z + n.toDouble())
	operator fun minus(v: Vec3) = Vec3(x - v.x, y - v.y, z - v.z)
	operator fun <T : Number> minus(n: T) = Vec3(x - n.toDouble(), y - n.toDouble(), z - n.toDouble())
	operator fun times(v: Vec3) = Vec3(x * v.x, y * v.y, z * v.z)
	operator fun <T : Number> times(n: T) = Vec3(x * n.toDouble(), y * n.toDouble(), z * n.toDouble())
	operator fun <T : Number> div(n: T) = Vec3(x / n.toDouble(), y / n.toDouble(), z / n.toDouble())
	infix fun dot(v: Vec3) = x * v.x + y * v.y + z * v.z
	infix fun cross(v: Vec3) = Vec3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)

	fun nearZero() = elements.all { abs(it) < 1E-8 }
	fun reflect(normal: Vec3) = this - 2 * (this dot normal) * normal
	fun refract(normal: Vec3, etaRatio: Double): Vec3 {
		val cosTheta = (-this dot normal).coerceAtMost(1.0)
		val rayPerp = etaRatio * (this + cosTheta * normal)
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
			while (true) {
				val p = random(-1.0, 1.0)
				if (p.length2 >= 1) continue
				return p
			}
		}
		fun randomUnitVector() = randomInUnitSphere().unit
		fun randomOnHemisphere(normal: Vec3) =
			randomUnitVector().let {
				if (it dot normal > 0.0) it else -it
			}
	}
}

operator fun <T : Number> T.times(v: Vec3) = v * this
