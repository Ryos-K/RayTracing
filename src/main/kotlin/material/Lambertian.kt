package material

import model.HitRecord
import model.Ray
import utils.Vec3

class Lambertian(override val attenuation: Vec3) : Material {
	override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? {
		return Ray(
			hitRecord.point,
			(hitRecord.normal + Vec3.randomUnitVector())
				.takeUnless { it.nearZero() } ?: hitRecord.normal
		)
	}
}