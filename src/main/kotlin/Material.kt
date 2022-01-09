import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

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

class Dielectric(val refractionIndex: Double) : Material() {

    override fun scatter(ray: Ray, hit: Hit): ScatterResult? {
        val attenuation = Colour(1, 1, 1)
        val refractionRatio = if (hit.frontFace) 1.0/refractionIndex else refractionIndex
        val rayUnitDir = ray.direction.unit()

        val cosTheta = min(Vec3.dot(-rayUnitDir, hit.normal), 1.0)
        val sinTheta = sqrt(1.0 - cosTheta * cosTheta)

        val cannotRefract = refractionRatio * sinTheta > 1.0
        val direction = if (cannotRefract || reflectance(cosTheta, refractionRatio) > randomNum())
            Vec3.reflect(rayUnitDir, hit.normal)
        else
            Vec3.refract(rayUnitDir, hit.normal, refractionRatio)

        return ScatterResult(attenuation, Ray(hit.p, direction))
    }

    companion object {
        fun reflectance(cosine: Double, refIndex: Double): Double {
            var r0 = (1-refIndex) / (1 + refIndex)
            r0 *= r0
            return r0 + (1 - r0) * (1 - cosine).pow(5)
        }
    }


}