package utils

fun Vec3.toColor(samplePerPixel: Int) = elements
	.map { it / samplePerPixel }
	.map { (it.coerceIn(0.0, 0.999) * 256).toInt() }
	.let { (it[0] shl 16) + (it[1] shl 8) + (it[2] shl 0) }
