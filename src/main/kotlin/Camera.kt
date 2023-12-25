import model.Ray
import utils.MutVec3
import utils.Vec3
import utils.times
import utils.toColor
import java.awt.image.BufferedImage
import kotlin.random.Random
import kotlin.random.Random.Default.nextDouble

class Camera {
	var aspectRatio = 1.0
	var imageWidth = 100
	var samplePerPixel = 10
	private var imageHeight: Int = (imageWidth / aspectRatio).toInt().coerceAtLeast(1)
	private lateinit var cameraCenter: Vec3
	private lateinit var deltaU: Vec3
	private lateinit var deltaV: Vec3
	private lateinit var pixelTopLeft: Vec3
	fun render(world: World): BufferedImage {
		initialize()
		val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)
		repeat(imageHeight) { y ->
			System.err.print("\rScanlines remaining: %03d".format(imageHeight - y))
			repeat(imageWidth) { x ->
				val pixelColor = MutVec3()
				repeat(samplePerPixel) {
					val ray = randomRay(x, y)
					pixelColor += rayColor(ray, world)
				}
				image.setRGB(x, y, pixelColor.toColor(samplePerPixel))
			}
		}
		println("\rDone.                        \n")
		return image
	}

	private fun initialize() {
		val focalLength = 1.0
		val viewportHeight = 2.0
		imageHeight = (imageWidth / aspectRatio).toInt().coerceAtLeast(1)
		cameraCenter = Vec3()
		val viewportWidth = viewportHeight / imageHeight * imageWidth

		val viewportU = Vec3(viewportWidth, 0.0, 0.0)
		val viewportV = Vec3(0.0, -viewportHeight, 0.0)
		deltaU = viewportU / imageWidth
		deltaV = viewportV / imageHeight

		val viewportTopLeft = cameraCenter - viewportU / 2.0 - viewportV / 2.0 - Vec3(0.0, 0.0, focalLength)
		pixelTopLeft = viewportTopLeft + deltaU / 2.0 + deltaV / 2.0
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

	private fun rayColor(ray: Ray, world: World): Vec3 {
		world.hit(ray, 0.0, Double.POSITIVE_INFINITY)
			?.let { return (0.5 * (it.normal + 1.0)) }

		return ((ray.vector.unit.y + 1.0) / 2.0)
			.let { a -> Vec3(1.0, 1.0, 1.0) * (1 - a) + Vec3(0.5, 0.7, 1.0) * a }
	}
}