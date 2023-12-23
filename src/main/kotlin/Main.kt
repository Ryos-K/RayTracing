import utils.MutVec3
import utils.Vec3
import utils.toColor
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

fun main(args: Array<String>) {
	if (args.size != 1) {
		System.err.println("Usage: MainKt <output file>")
		exitProcess(1)
	}
	val outputFile = args[0]

	val (imageWidth, imageHeight) = 256 to 256
	val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)

	repeat(imageHeight) { y ->
		System.err.print("\rScanlines remaining: %03d".format(imageHeight - y))
		repeat(imageWidth) { x ->
			val mv = MutVec3()
			val v = Vec3(x.toDouble() / (imageWidth - 1), y.toDouble() / (imageHeight - 1), 1.0)
			mv += v
			image.setRGB(x, y, mv.toColor())
		}
	}

	println("\rDone.                        ")
	val file = File(outputFile)
	ImageIO.write(image, "png", file)
}