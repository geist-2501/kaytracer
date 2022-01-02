class Camera {

    val origin: Vec3
    val uvOrigin: Vec3
    val horizontal: Vec3
    val vertical: Vec3

    init {
        val aspect = 16.0 / 9.0
        val height = 2.0
        val width = aspect * height
        val focalLength = 1.0

        origin = Vec3()
        horizontal = Vec3(width, 0.0, 0.0)
        vertical = Vec3(0.0, height, 0.0)
        uvOrigin = origin - horizontal / 2.0 - vertical / 2.0 - Vec3(0.0, 0.0, focalLength)
    }

    fun getRay(u: Double, v: Double): Ray {
        return Ray(origin, uvOrigin + u * horizontal + v * vertical - origin)
    }

}