package material

import model.HitRecord
import model.Ray
import utils.Vec3
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Dielectric(val refractiveIndex: Double) : Material {
	override val attenuation = Vec3(1.0, 1.0, 1.0)

	override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? {
		val refractionRatio = if (hitRecord.front) 1.0 / refractiveIndex else refractiveIndex
		val cosTheta = (-ray.vector.unit dot hitRecord.normal).coerceAtMost(1.0)
		val sinTheta = sqrt(1.0 - cosTheta * cosTheta)
		val canRefract = refractionRatio * sinTheta < 1.0
		return Ray(
			hitRecord.point,
			if (canRefract && reflectance(cosTheta, refractionRatio) <= Random.nextDouble()) {
				ray.vector.unit.refract(hitRecord.normal, refractionRatio)
			} else {
				ray.vector.unit.reflect(hitRecord.normal)
			}
		)
	}
}

fun reflectance(cos: Double, refractiveIndex: Double) =
	((1 - refractiveIndex) / (1 + refractiveIndex))
		.let { it * it }
		.let { it + (1 - it) * (1 - cos).pow(5) }