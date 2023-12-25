import body.Sphere
import model.Ray
import utils.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random
import kotlin.system.exitProcess

fun main(args: Array<String>) {
	if (args.size != 1) {
		System.err.println("Usage: MainKt <output file>")
		exitProcess(1)
	}
	val outputFile = args[0]

	val world = World(
		listOf(
			Sphere(Vec3(0.0, 0.0, -1.0), 0.5),
			Sphere(Vec3(0.0, -100.5, -1.0), 100.0),
		)
	)
	val camera = Camera()
	camera.aspectRatio = 16.0 / 9.0
	camera.imageWidth = 400
	camera.samplePerPixel = 100
	camera.maxDepth = 10
	val image = camera.render(world)

	val file = File(outputFile)
	ImageIO.write(image, "png", file)
}

