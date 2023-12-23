package utils

class MutVec3(
	x: Double,
	y: Double,
	z: Double,
) : Vec3(x, y, z) {
	constructor(): this(0.0, 0.0, 0.0)
	override var x: Double
		get() = elements[0]
		set(value) {
			elements[0] = value
		}
	override var y: Double
		get() = elements[1]
		set(value) {
			elements[1] = value
		}
	override var z: Double
		get() = elements[2]
		set(value) {
			elements[2] = value
		}

	operator fun set(i: Int, value: Double) {
		elements[i] = value
	}
	operator fun plusAssign(v: Vec3) {
		repeat(3) {elements[it] += v.elements[it]}
	}
	operator fun minusAssign(v: Vec3) {
		repeat(3) {elements[it] -= v.elements[it]}
	}
	operator fun timesAssign(v: Vec3) {
		repeat(3) {elements[it] *= v.elements[it]}
	}
	operator fun divAssign(v: Vec3) {
		repeat(3) {elements[it] /= v.elements[it]}
	}

}