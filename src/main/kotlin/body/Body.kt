package body

import material.Material
import model.HitRecord
import model.Ray

interface Body {
	val material: Material
	fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord?
}