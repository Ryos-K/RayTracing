import body.Sphere
import material.Dielectric
import material.Lambertian
import material.Metal
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

	val materialGround = Lambertian(Vec3(0.8, 0.8, 0.0))
	val materialCenter = Lambertian(Vec3(0.1, 0.2, 0.5))
	val materialLeft = Dielectric(1.5)
	val materialRight = Metal(Vec3(0.8, 0.6, 0.2), 0.1)

	val world = World(
		listOf(
			Sphere(Vec3(0.0, -100.5, -1.0), 100.0, materialGround),
			Sphere(Vec3(0.0, 0.0, -1.0), 0.5, materialCenter),
			Sphere(Vec3(-1.0, 0.0, -1.0), 0.5, materialLeft),
			Sphere(Vec3(-1.0, 0.0, -1.0), -0.4, materialLeft),
			Sphere(Vec3(1.0, 0.0, -1.0), 0.5, materialRight),
		)
	)
	val camera = Camera()
	camera.aspectRatio = 16.0 / 9.0
	camera.imageWidth = 400
	camera.samplePerPixel = 50
	camera.maxDepth = 20
	camera.verticalFov = 90.0
	camera.lookFrom = Vec3(-2.0, 2.0, 1.0)
	camera.lookAt = Vec3(0.0, 0.0, -1.0)
	camera.upDir = Vec3(0.0, 1.0, 0.0)

	val image = camera.render(world)

	val file = File(outputFile)
	ImageIO.write(image, "png", file)
}

