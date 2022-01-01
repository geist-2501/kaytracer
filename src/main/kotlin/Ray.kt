/**
 * Representation of an infinite Ray, that has origin and direction.
 */
class Ray(val origin: Vec3, val direction: Vec3) {

    fun at(t: Double): Vec3 {
        return origin + t * direction
    }

}