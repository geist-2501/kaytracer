data class ScatterResult(val attenuation: Colour, val scattered: Ray)

abstract class Material {

    abstract fun scatter(ray: Ray, hit: Hit): ScatterResult?

}

class Lambertian(val albedo: Colour) : Material() {

    override fun scatter(ray: Ray, hit: Hit): ScatterResult? {
        var scatterDir = hit.normal + Vec3.randomUnitVector()

        if (scatterDir.isNearZero())
            scatterDir = hit.normal

        val scattered = Ray(hit.p, scatterDir)
        return ScatterResult(albedo, scattered)
    }

}

class Metal(val albedo: Colour, f: Double) : Material() {

    val fuzz = if (f < 1.0) f else 1.0

    override fun scatter(ray: Ray, hit: Hit): ScatterResult? {
        val reflected = Vec3.reflect(ray.direction.unit(), hit.normal)
        val fuzzOffset = fuzz * Vec3.randomInUnitSphere()
        val scattered = Ray(hit.p, reflected + fuzzOffset)
        return if (Vec3.dot(scattered.direction, hit.normal) > 0) ScatterResult(albedo, scattered) else null
    }

}