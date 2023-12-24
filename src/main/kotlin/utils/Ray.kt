package utils

class Ray(
	val origin: Vec3,
	val vector: Vec3,
) {
	fun at(t: Double) = origin + vector * t

	fun inSphere(center: Vec3, radius: Double) : Boolean{
		val oc = center - origin
		val a = vector dot vector
		val b = 2 * (oc dot vector)
		val c = (oc dot oc) - radius * radius
		val discriminant = b * b - 4 * a * c
		return discriminant >= 0
	}
}