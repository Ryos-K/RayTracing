import model.HitRecord
import model.Ray
import utils.MutVec3
import utils.Vec3
import utils.times
import utils.toColor
import java.awt.image.BufferedImage
import kotlin.math.tan
import kotlin.random.Random
import kotlin.random.Random.Default.nextDouble

class Camera {
	var aspectRatio = 1.0
	var imageWidth = 100
	var samplePerPixel = 10
	var maxDepth = 10
	var verticalFov = 90.0
	var lookFrom = Vec3(0.0,0.0,0.0)
	var lookAt = Vec3(0.0,0.0,-1.0)
	var upDir = Vec3(0.0, 1.0, 0.0)
	private var imageHeight: Int = (imageWidth / aspectRatio).toInt().coerceAtLeast(1)
	private lateinit var cameraCenter: Vec3
	private lateinit var pixelTopLeft: Vec3
	private lateinit var deltaU: Vec3
	private lateinit var deltaV: Vec3
	private lateinit var u: Vec3
	private lateinit var v: Vec3
	private lateinit var w: Vec3
	fun render(world: World): BufferedImage {
		initialize()
		val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)
		repeat(imageHeight) { y ->
			System.err.print("\rScanlines remaining: %03d".format(imageHeight - y))
			repeat(imageWidth) { x ->
				val pixelColor = MutVec3()
				repeat(samplePerPixel) {
					val ray = randomRay(x, y)
					pixelColor += rayColor(ray, world, 1)
				}
				image.setRGB(x, y, pixelColor.toColor(samplePerPixel))
			}
		}
		println("\rDone.                        \n")
		return image
	}

	private fun initialize() {
		imageHeight = (imageWidth / aspectRatio).toInt().coerceAtLeast(1)

		val focalLength = (lookFrom - lookAt).length
		cameraCenter = lookFrom
		val viewportHeight = 2 * tan(verticalFov.toRadian() / 2) * focalLength
		val viewportWidth = viewportHeight / imageHeight * imageWidth

		w = (lookFrom - lookAt).unit
		u = upDir cross w
		v = w cross u
		val viewportU = viewportWidth * u
		val viewportV = viewportHeight * -v
		deltaU = viewportU / imageWidth
		deltaV = viewportV / imageHeight

		val viewportTopLeft = cameraCenter - viewportU / 2.0 - viewportV / 2.0 - focalLength * w
		pixelTopLeft = viewportTopLeft + deltaU / 2.0 + deltaV / 2.0

		println("""
			|Camera:
			|  lookFrom: $lookFrom
			|  lookAt: $lookAt
			|  upDir: $upDir
			|  aspectRatio: $aspectRatio
			|  imageWidth: $imageWidth
			|  imageHeight: $imageHeight
			|  samplePerPixel: $samplePerPixel
			|  maxDepth: $maxDepth
			|  verticalFov: $verticalFov
			|  cameraCenter: $cameraCenter
			|  pixelTopLeft: $pixelTopLeft
			|  deltaU: $deltaU
			|  deltaV: $deltaV
			|  u: $u
			|  v: $v
			|  w: $w
		""".trimIndent())
	}

	private fun randomRay(x: Int, y: Int): Ray {
		val pixelCenter = pixelTopLeft + deltaU * x + deltaV * y
		val pixelRandom = pixelCenter + randomInSquare()
		val origin = cameraCenter
		val vector = pixelRandom - origin
		return Ray(origin, vector)
	}

	private fun randomInSquare() =
		(-0.5 + nextDouble()) * deltaU + (-0.5 + nextDouble()) * deltaV

	private fun rayColor(ray: Ray, world: World, depth: Int): Vec3 {
		if (depth > maxDepth) return Vec3()

		world.hit(ray, 0.01, Double.POSITIVE_INFINITY)?.let { record ->
			return record.material.scatter(ray, record)?.let {
				record.material.attenuation * rayColor(it, world, depth + 1)
			} ?: Vec3()
		}

		return ((ray.vector.unit.y + 1.0) / 2.0)
			.let { a -> Vec3(1.0, 1.0, 1.0) * (1 - a) + Vec3(0.5, 0.7, 1.0) * a }
	}
}

private fun Double.toRadian() = this / 180.0 * Math.PI
