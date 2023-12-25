package model

import utils.Vec3

data class Ray(
	val origin: Vec3,
	val vector: Vec3,
) {
	fun at(t: Double) = origin + vector * t
}