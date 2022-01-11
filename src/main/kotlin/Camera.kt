import kotlin.math.tan

class Camera(lookFrom: Vec3, lookAt: Vec3, vup: Vec3, vfov: Double, aspect: Double) {

    private val origin: Vec3
    private val uvOrigin: Vec3
    private val horizontal: Vec3
    private val vertical: Vec3

    init {
        val theta = Math.toRadians(vfov)
        val h = tan(theta / 2)
        val height = 2.0 * h
        val width = aspect * height

        val w = (lookFrom - lookAt).unit()
        val u = Vec3.cross(vup, w).unit()
        val v = Vec3.cross(w, u)

        origin = lookFrom
        horizontal = width * u
        vertical = height * v
        uvOrigin = origin - horizontal / 2.0 - vertical / 2.0 - w
    }

    fun getRay(s: Double, t: Double): Ray {
        return Ray(origin, uvOrigin + s * horizontal + t * vertical - origin)
    }

}