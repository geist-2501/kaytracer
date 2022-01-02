
class Hit(val p: Vec3, val t: Double, r: Ray, outwardNormal: Vec3) {

    val frontFace: Boolean = Vec3.dot(r.direction, outwardNormal) < 0
    val normal: Vec3 = if (frontFace) outwardNormal else -outwardNormal
}

abstract class Hittable {
    abstract fun hit(r: Ray, min: Double, max: Double): Hit?
}