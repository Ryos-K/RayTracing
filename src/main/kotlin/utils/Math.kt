package utils

import kotlin.math.pow

fun Double.toRadian() = this / 180.0 * Math.PI

fun reflectance(cos: Double, refractiveIndex: Double) =
	((1 - refractiveIndex) / (1 + refractiveIndex))
		.let { it * it }
		.let { it + (1 - it) * (1 - cos).pow(5) }