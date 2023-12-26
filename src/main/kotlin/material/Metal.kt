package material

import model.HitRecord
import model.Ray
import utils.Vec3

class Metal(override val attenuation: Vec3) : Material {
	override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? {
		return Ray(
			hitRecord.point,
			ray.vector.reflect(hitRecord.normal)
		)
	}
}