package model

import material.Material
import utils.Vec3

data class HitRecord(
	val point: Vec3,
	val normal: Vec3,
	val material: Material,
	val t: Double,
	val front: Boolean
)
