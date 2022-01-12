import java.io.File

fun rayColour(r: Ray, world: Hittable, depth: Int): Colour {
    if (depth <= 0) {
        return Colour(0, 0, 0)
    }

    val hit = world.hit(r, 0.001, Double.POSITIVE_INFINITY)
    if (hit != null) {
        val scatter = hit.material.scatter(r, hit)
        if (scatter != null) {
            return scatter.attenuation * rayColour(scatter.scattered, world, depth - 1)
        }

        return Colour(0, 0, 0)
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

    val matGround = Lambertian(Colour(0.8, 0.8, 0.0))
    val matCenter = Dielectric(1.5)
    val matLeft = Metal(Colour(0.8, 0.8, 0.8), 0.1)
    val matRight = Metal(Colour(0.8, 0.6, 0.2), 1.0)

    val world = HittableList()
    world.add(Sphere(Vec3(0.0, -100.5, -1.0), 100.0, matGround))
    world.add(Sphere(Vec3(0, 0, -1), 0.5, matCenter))
    world.add(Sphere(Vec3(-1, 0, -1), 0.5, matLeft))
    world.add(Sphere(Vec3(1, 0, -1), 0.5, matRight))

    // Camera.

    val lookFrom = Vec3(-2, 2, 1)
    val lookAt = Vec3(0, 0, -1)
    val vup = Vec3(0, 1, 0)
    val focusDist = (lookFrom - lookAt).mag()
    val camera = Camera(lookFrom, lookAt, vup, 20.0, aspect, 1.5, focusDist)

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

