package material

import model.HitRecord
import model.Ray
import utils.Vec3
import utils.reflectance
import kotlin.math.sqrt
import kotlin.random.Random

class Dielectric(val refractiveIndex: Double) : Material {
	override val attenuation = Vec3(1.0, 1.0, 1.0)

	override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? {
		val refractionRatio = if (hitRecord.front) 1.0 / refractiveIndex else refractiveIndex
		val cos = (-ray.vector.unit dot hitRecord.normal).coerceAtMost(1.0)
		val sin = sqrt(1.0 - cos * cos)
		val canRefract = refractionRatio * sin < 1.0
		return Ray(
			origin = hitRecord.point,
			vector = if (canRefract && reflectance(cos, refractionRatio) <= Random.nextDouble()) {
				ray.vector.unit.refract(hitRecord.normal, refractionRatio)
			} else {
				ray.vector.unit.reflect(hitRecord.normal)
			}
		)
	}
}

