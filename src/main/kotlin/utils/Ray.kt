package utils

class Ray(
	val origin: Vec3,
	val vector: Vec3,
) {
	fun at(t: Double) = origin + vector * t
}