package utils

import kotlin.math.sqrt

class Ray(
	val origin: Vec3,
	val vector: Vec3,
) {
	fun at(t: Double) = origin + vector * t

	fun timeOfIntersection(center: Vec3, radius: Double) : Double {
		val oc = origin - center
		val a = vector.length2
		val harfB = 2 * (oc dot vector)
		val c = oc.length2 - radius * radius
		val discriminant = harfB * harfB - a * c
		return (-harfB - sqrt(discriminant)) / a
	}
}