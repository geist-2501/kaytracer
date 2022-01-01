import java.io.File

fun rayColour(r: Ray): Colour {
    if (hitSphere(Vec3(0, 0, -1), 0.5, r)) {
        return Colour(1, 0, 0)
    }

    val unitDir = r.direction.unit()
    val t = 0.5 * (unitDir.y + 1.0)
    return (1.0 - t) * Colour(1, 1, 1) + t * Colour(0.5, 0.7, 1.0)
}

fun hitSphere(center: Vec3, radius: Double, r: Ray): Boolean {

    val oc = r.origin - center
    val a = Vec3.dot(r.direction, r.direction)
    val b = 2.0 * Vec3.dot(oc, r.direction)
    val c = Vec3.dot(oc, oc) - radius * radius
    val discriminant = b*b - 4 * a * c

    return discriminant > 0
}

fun main(args: Array<String>) {

    val f = File("img.ppm").bufferedWriter()

    // Image

    val aspect = 16.0 / 9.0
    val width = 400
    val height = (width / aspect).toInt()

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
            val colour = rayColour(r)

            writeColour(f, colour)
        }
    }

    f.close()

    println("Done!")
}

