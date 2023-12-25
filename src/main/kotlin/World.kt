import body.Body
import body.Sphere
import model.HitRecord
import utils.Ray
import utils.Vec3

class World(c: Collection<Body>) : ArrayList<Body>(c), Body {
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