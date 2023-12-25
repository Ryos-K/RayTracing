package body

import model.HitRecord
import model.Ray

interface Body {
	fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord?
}