package material

import model.HitRecord
import model.Ray
import utils.Vec3
import utils.times

class Metal(override val attenuation: Vec3, val fuzz: Double) : Material {
	override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? =
		(ray.vector.unit.reflect(hitRecord.normal) + fuzz * Vec3.randomUnitVector())
			.takeIf { (it dot hitRecord.normal) > 0.0 } // else return null
			?.let { vector ->
				Ray(
					origin = hitRecord.point,
					vector = vector
				)
			}
}