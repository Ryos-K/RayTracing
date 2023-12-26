import body.Body
import material.Material
import model.HitRecord
import model.Ray
import utils.Vec3

class World(c: Collection<Body>) : ArrayList<Body>(c), Body {
	override val material: Material
		get() = object : Material {
			override val attenuation = Vec3()
			override fun scatter(ray: Ray, hitRecord: HitRecord): Ray? = null
		}

	override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
		var closestHitRecord: HitRecord? = null
		var closestT = tMax
		for (body in this) {
			body.hit(ray, tMin, closestT)?.let { hitRecord ->
				closestHitRecord = hitRecord
				closestT = hitRecord.t
			}
		}
		return closestHitRecord
	}
}