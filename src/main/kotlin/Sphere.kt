import kotlin.math.sqrt

class Sphere(val center: Vec3, val radius: Double) : Hittable() {

    override fun hit(ray: Ray, min: Double, max: Double): Hit? {
        val oc = ray.origin - center
        val a = ray.direction.sqrMag()
        val halfB = Vec3.dot(oc, ray.direction)
        val c = oc.sqrMag() - radius * radius

        val discriminant = halfB * halfB - a * c
        if (discriminant < 0) {
            return null
        }

        val sqrtd = sqrt(discriminant)

        // Find the nearest root.

        // Try first root.
        var root = (-halfB - sqrtd) / a
        if (root < min || max < root) {
            // Try second.
            root = (-halfB + sqrtd) / a
            if (root < min || max < root) {
                return null
            }
        }

        val intersection = ray.at(root)
        val outwardNormal = (intersection - center) / radius

        return Hit(intersection, root, ray, outwardNormal)
    }
}