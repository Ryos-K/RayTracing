package material

import model.HitRecord
import model.Ray
import utils.Vec3

interface Material {
	val attenuation: Vec3
		get() = Vec3()
	val emitted: Vec3
		get() = Vec3()

	fun scatter(ray: Ray, hitRecord: HitRecord): Ray?
}