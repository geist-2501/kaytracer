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

    // World.

    val world = HittableList()
    world.add(Sphere(Vec3(0, 0, -1), 0.5))
    world.add(Sphere(Vec3(0.0, -100.5, -1.0), 100.0))

    // Camera.

    val viewportHeight = 2.0
    val viewportWidth = viewportHeight * aspect
    val focalLength = 1.0

    val origin = Vec3()
    val horizontal = Vec3(viewportWidth, 0.0, 0.0)
    val vertical = Vec3(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)

    // Render.

    f.write("P3\n")
    f.write("$width $height\n")
    f.write("256\n")

    for (j in height - 1 downTo 0) {
        for (i in 0 until width) {
            val u = i.toDouble() / (width - 1)
            val v = j.toDouble() / (height - 1)
            val r = Ray(origin, lowerLeftCorner + u * horizontal + v * vertical - origin)
            val colour = rayColour(r, world)

            writeColour(f, colour)
        }
    }

    f.close()

    println("Done!")
}

