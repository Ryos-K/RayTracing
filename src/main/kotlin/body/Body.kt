package body

import model.HitRecord
import utils.Ray

interface Body {
	fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord?
}