import java.io.File

fun rayColour(r: Ray, world: Hittable, depth: Int): Colour {
    if (depth <= 0) {
        return Colour(0, 0, 0)
    }

    val hit = world.hit(r, 0.001, Double.POSITIVE_INFINITY)
    if (hit != null) {
        val target = hit.p + hit.normal + Vec3.randomUnitVector()
//        val target = hit.p + Vec3.randomInHemisphere(hit.normal)
        val bounce = Ray(hit.p, target - hit.p)
        return 0.5 * rayColour(bounce, world, depth - 1)
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
    val maxBounces = 50

    // World.

    val world = HittableList()
    world.add(Sphere(Vec3(0, 0, -1), 0.5))
    world.add(Sphere(Vec3(0.0, -100.5, -1.0), 100.0))

    // Camera.

    val camera = Camera()

    // Render.

    f.write("P3\n")
    f.write("$width $height\n")
    f.write("255\n")

    println("Beginning render...")

    for (j in height - 1 downTo 0) {
        for (i in 0 until width) {
            val colour = Colour()
            for (s in 0 until samplesPerPixel) {
                val u = (i + randomNum()) / (width - 1)
                val v = (j + randomNum()) / (height - 1)
                val r = camera.getRay(u, v)

                colour += rayColour(r, world, maxBounces)
            }

            writeColour(f, colour, samplesPerPixel)
        }

        if (j % 20 == 0) print("#")
    }

    println()

    f.close()

    println("Done!")
}

