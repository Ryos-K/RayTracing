package body

import material.Material
import model.HitRecord
import model.Ray
import utils.Vec3
import kotlin.math.sqrt

data class Sphere(
	val center: Vec3,
	val radius: Double,
	override val material: Material
) : Body {
	override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
		val oc = ray.origin - center
		val a = ray.vector.length2
		val harfB = oc dot ray.vector
		val c = oc.length2 - radius * radius
		val discriminant = (harfB * harfB - a * c).takeIf { it >= 0 } ?: return null

		val sqrtDiscriminant = sqrt(discriminant)
		val t = when {
			(-harfB - sqrtDiscriminant) / a in tMin..tMax -> (-harfB - sqrtDiscriminant) / a
			(-harfB + sqrtDiscriminant) / a in tMin..tMax -> (-harfB + sqrtDiscriminant) / a
			else                                          -> return null
		}
		val point = ray.at(t)
		val outwardNormal = (point - center) / radius
		val front = (ray.vector dot outwardNormal) < 0
		return HitRecord(point, if (front) outwardNormal else -outwardNormal, material, t, front)
	}
}
