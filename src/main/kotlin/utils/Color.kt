package utils

fun Vec3.toColor() = ((x * 255.999).toInt() shl 16) + ((y * 255.999).toInt() shl 8) + ((z * 255.999).toInt() shl 0)
