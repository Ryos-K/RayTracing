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
import kotlin.random.Random.Default.nextDouble
import kotlin.system.exitProcess

fun main(args: Array<String>) {
	if (args.size != 1) {
		System.err.println("Usage: MainKt <output file>")
		exitProcess(1)
	}
	val outputFile = args[0]


	val world = World(
		listOf(
			// Ground
			Sphere(Vec3(0.0, -1000.0, 0.0), 1000.0, Lambertian(Vec3(0.5, 0.5, 0.5))),
			// Objects
			Sphere(Vec3(0.0, 1.0, 0.0), 1.0, Dielectric(1.5)),
			Sphere(Vec3(-4.0, 1.0, 0.0), 1.0, Lambertian(Vec3(0.4, 0.2, 0.1))),
			Sphere(Vec3(4.0, 1.0, 0.0), 1.0, Metal(Vec3(0.7, 0.6, 0.5), 0.0))
		)
	)
	for (a in -11..11) {
		for (b in -11..11) {
			val center = Vec3(a + 0.9 * nextDouble(), 0.2, b + 0.9 * nextDouble())
				.takeIf { (it - Vec3(4.0, 0.2, 0.0)).length > 0.9 } ?: continue
			world.add(
				Sphere(
					center,
					0.2,
					when (nextDouble()) {
						in 0.0..0.8  -> Lambertian(Vec3.random() * Vec3.random())
						in 0.8..0.95 -> Metal(Vec3.random(0.5, 1.0), nextDouble(0.0, 0.5))
						else         -> Dielectric(1.5)
					}
				)
			)
		}
	}

	val image = Camera().apply {
		aspectRatio = 16.0 / 9.0
		imageWidth = 400
		samplePerPixel = 50
		maxDepth = 10
		verticalFov = 20.0
		lookFrom = Vec3(13.0, 2.0, 3.0)
		lookAt = Vec3(0.0, 0.0, 0.0)
		upDir = Vec3(0.0, 1.0, 0.0)
		defocusAngle = 0.6
		focusDist = 10.0
	}.render(world)

	val file = File(outputFile)
	ImageIO.write(image, "png", file)
}

