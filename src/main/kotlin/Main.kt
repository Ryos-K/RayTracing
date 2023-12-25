import body.Body
import body.Sphere
import utils.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

// Image
const val aspectRatio = 16.0 / 9.0
const val imageWidth = 400
val imageHeight = (imageWidth / aspectRatio).toInt()//.coerceAtLeast(1)

// Camera
const val focalLength = 1.0
const val viewportHeight = 2.0
val viewportWidth = viewportHeight / imageHeight * imageWidth
val cameraCenter = Vec3()

// Viewport
val viewportU = Vec3(viewportWidth, 0.0, 0.0)
val viewportV = Vec3(0.0, -viewportHeight, 0.0)
val deltaU = viewportU / imageWidth
val deltaV = viewportV / imageHeight
val viewportTopLeft = cameraCenter - viewportU / 2.0 - viewportV / 2.0 - Vec3(0.0, 0.0, focalLength)
val pixelTopLeft = viewportTopLeft + deltaU / 2.0 + deltaV / 2.0

fun main(args: Array<String>) {
	if (args.size != 1) {
		System.err.println("Usage: MainKt <output file>")
		exitProcess(1)
	}
	val outputFile = args[0]
	val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)

	val world = World(
		listOf(
			Sphere(Vec3(0.0, 0.0, -1.0), 0.5),
			Sphere(Vec3(0.0, -100.5, -1.0), 100.0),
		)
	)
	repeat(imageHeight) { y ->
		System.err.print("\rScanlines remaining: %03d".format(imageHeight - y))
		repeat(imageWidth) { x ->
			val pixel = pixelTopLeft + deltaU * x + deltaV * y
			val rayDirection = pixel - cameraCenter
			val ray = Ray(cameraCenter, rayDirection)

			image.setRGB(x, y, ray.rayColor(world))
		}
	}

	println("\rDone.                        ")
	val file = File(outputFile)
	ImageIO.write(image, "png", file)
}

fun Ray.rayColor(world: World): Int {
	world.hit(this, 0.0, Double.POSITIVE_INFINITY)?.let {
		return (0.5 * (it.normal + 1.0)).toColor()
	}

	return ((vector.unit.y + 1.0) / 2.0).let { a -> Vec3(1.0, 1.0, 1.0) * (1 - a) + Vec3(0.5, 0.7, 1.0) * a }.toColor()
}
