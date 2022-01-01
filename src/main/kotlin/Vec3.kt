import kotlin.math.sqrt

class Vec3(x: Double, y: Double, z: Double) {

    private val e: Array<Double> = arrayOf(x, y, z)

    var x: Double
        get() = e[0]
        set(value) { e[0] = value }

    var y: Double
        get() = e[1]
        set(value) { e[1] = value }

    var z: Double
        get() = e[2]
        set(value) { e[2] = value }

    constructor() : this(0.0, 0.0, 0.0)
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    operator fun unaryMinus(): Vec3 {
        return Vec3(-x, -y, -z)
    }

    operator fun plusAssign(o: Vec3) {
        x += o.x
        y += o.y
        z += o.z
    }

    operator fun timesAssign(f: Double) {
        x *= f
        y *= f
        z *= f
    }

    operator fun divAssign(f: Double) {
        this *= 1/f
    }

    operator fun plus(o: Vec3): Vec3 {
        return Vec3(x + o.x, y + o.y, z + o.z)
    }

    operator fun minus(o: Vec3): Vec3 {
        return Vec3(x - o.x, y - o.y, z - o.z)
    }

    operator fun times(o: Vec3): Vec3 {
        return Vec3(x * o.x, y * o.y, z * o.z)
    }

    operator fun times(d: Double): Vec3 {
        return Vec3(x * d, y * d, z * d)
    }

    operator fun div(d: Double): Vec3 {
        return Vec3(x / d, y / d, z / d)
    }

    fun mag(): Double {
        return sqrt(sqrMag())
    }

    fun sqrMag(): Double {
        return x * x + y * y + z * z
    }

    fun unit(): Vec3 {
        return this / mag()
    }

    companion object {
        fun dot(a: Vec3, b: Vec3): Double {
            return a.x * b.x + a.y * b.y + a.z * b.z
        }

        fun cross(a: Vec3, b: Vec3): Vec3 {
            return Vec3(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x
            )
        }
    }

    override fun toString(): String {
        return "[$x, $y, $z]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vec3

        if (!e.contentEquals(other.e)) return false

        return true
    }

    override fun hashCode(): Int {
        return e.contentHashCode()
    }
}

operator fun Double.times(o: Vec3): Vec3 {
    return Vec3(o.x * this, o.y * this, o.z * this)
}

typealias Colour = Vec3