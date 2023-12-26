package material

import model.HitRecord
import model.Ray
import utils.Vec3
import utils.times

class Metal(override val attenuation: Vec3, val fuzz: Double) : Material {
	override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? {
		val vector =
			(ray.vector.unit.reflect(hitRecord.normal) + fuzz * Vec3.randomUnitVector())
				.takeIf { (it dot hitRecord.normal) > 0.0 } ?: return null
		return Ray(hitRecord.point, vector)
	}
}