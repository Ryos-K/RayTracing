import utils.MutVec3
import utils.Ray
import utils.Vec3
import utils.toColor
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess
import kotlin.time.times

// Image
const val aspectRatio = 16.0 / 9.0
const val imageWidth = 400
val imageHeight = (imageWidth / aspectRatio).toInt().coerceAtLeast(1)
// Camera
const val focalLength = 1.0
const val viewportHeight = 2.0
val viewportWidth = viewportHeight * (imageWidth / imageHeight)
val cameraCenter = Vec3()
// Viewport
val viewportU = Vec3(viewportWidth, 0.0, 0.0)
val viewportV = Vec3(0.0, viewportHeight, 0.0)
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

	repeat(imageHeight) { y ->
		System.err.print("\rScanlines remaining: %03d".format(imageHeight - y))
		repeat(imageWidth) { x ->
			val pixel = pixelTopLeft + deltaU * x + deltaV * y
			val rayDirection = pixel - cameraCenter
			val ray = Ray(cameraCenter, rayDirection)

			image.setRGB(x, y, ray.rayColor())
		}
	}

	println("\rDone.                        ")
	val file = File(outputFile)
	ImageIO.write(image, "png", file)
}

fun Ray.rayColor() = ((vector.unit.y + 1.0) / 2.0).let {a -> Vec3(1.0, 1.0, 1.0) * (1 - a) + Vec3(0.5, 0.2, 0.8) * a}.toColor()