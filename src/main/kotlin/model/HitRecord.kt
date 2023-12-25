package model

import utils.Vec3

data class HitRecord(
	val point: Vec3,
	val normal: Vec3,
	val t: Double,
	val front: Boolean
)
