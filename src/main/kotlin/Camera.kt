import kotlin.math.tan

class Camera(lookFrom: Vec3, lookAt: Vec3, vup: Vec3, vfov: Double, aspect: Double, aperture: Double, focusDist: Double) {

    private val origin: Vec3
    private val uvOrigin: Vec3
    private val horizontal: Vec3
    private val vertical: Vec3
    private val u: Vec3
    private val v: Vec3
    private val w: Vec3
    private val lensRadius: Double

    init {
        val theta = Math.toRadians(vfov)
        val h = tan(theta / 2)
        val height = 2.0 * h
        val width = aspect * height

        w = (lookFrom - lookAt).unit()
        u = Vec3.cross(vup, w).unit()
        v = Vec3.cross(w, u)

        origin = lookFrom
        horizontal = focusDist * width * u
        vertical = focusDist * height * v
        uvOrigin = origin - horizontal / 2.0 - vertical / 2.0 - focusDist * w

        lensRadius = aperture / 2
    }

    fun getRay(s: Double, t: Double): Ray {
        val rd = lensRadius * Vec3.randomInUnitDisk()
        val offset = u * rd.x + v * rd.y

        return Ray(origin + offset, uvOrigin + s * horizontal + t * vertical - origin - offset)
    }

}