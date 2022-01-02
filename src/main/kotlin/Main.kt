import java.io.File
import kotlin.math.sqrt

fun rayColour(r: Ray, world: Hittable): Colour {
    val hit = world.hit(r, 0.0, Double.POSITIVE_INFINITY)
    if (hit != null) {
        return 0.5 * (hit.normal + Colour(1, 1, 1))
    }

    val unitDir = r.direction.unit()
    val t = 0.5 * (unitDir.y + 1.0)
    return (1.0 - t) * Colour(1, 1, 1) + t * Colour(0.5, 0.7, 1.0)
}

fun main(args: Array<String>) {

    val f = File("img.ppm").bufferedWriter()

    // Image

    val aspect = 16.0 / 9.0
    val width = 400
    val height = (width / aspect).toInt()
    val samplesPerPixel = 100

    // World.

    val world = HittableList()
    world.add(Sphere(Vec3(0, 0, -1), 0.5))
    world.add(Sphere(Vec3(0.0, -100.5, -1.0), 100.0))

    // Camera.

    val camera = Camera()

    // Render.

    f.write("P3\n")
    f.write("$width $height\n")
    f.write("256\n")

    for (j in height - 1 downTo 0) {
        for (i in 0 until width) {
            val colour = Colour()
            for (s in 0 until samplesPerPixel) {
                val u = (i + randomNum()) / (width - 1)
                val v = (j + randomNum()) / (height - 1)
                val r = camera.getRay(u, v)

                colour += rayColour(r, world)
            }

            writeColour(f, colour, samplesPerPixel)
        }
    }

    f.close()

    println("Done!")
}

