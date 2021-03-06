import kotlin.math.abs
import kotlin.math.min
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

    fun isNearZero(): Boolean {
        val s = 1e-8
        return (abs(x) < s) && (abs(y) < s) && (abs(z) < s)
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

        fun random(): Vec3 {
            return Vec3(randomNum(), randomNum(), randomNum())
        }

        fun random(min: Double, max: Double): Vec3 {
            return Vec3(randomNum(min, max), randomNum(min, max), randomNum(min, max))
        }

        fun randomInUnitSphere(): Vec3 {
            while (true) {
                val v = random(-1.0, 1.0)
                if (v.sqrMag() >= 1.0) continue
                return v
            }
        }

        fun randomUnitVector(): Vec3 {
            return randomInUnitSphere().unit()
        }

        fun randomInUnitDisk(): Vec3 {
            while (true) {
                val p = Vec3(randomNum(-1.0, 1.0), randomNum(-1.0, 1.0), 0.0)
                if (p.sqrMag() >= 1) continue
                return p
            }
        }

        fun randomInHemisphere(normal: Vec3): Vec3 {
            val inSphere = randomInUnitSphere()
            return if (dot(inSphere, normal) > 0.0) inSphere else -inSphere
        }

        fun reflect(v: Vec3, n: Vec3): Vec3 {
            return v - 2.0 * dot(v, n) * n
        }

        fun refract(uv: Vec3, n: Vec3, etaiOverEtat: Double): Vec3 {
            val cosTheta = min(dot(-uv, n), 1.0)
            val rOutPerp = etaiOverEtat * (uv + cosTheta * n)
            val rOutPara = -sqrt(abs(1.0 - rOutPerp.sqrMag())) * n
            return rOutPerp + rOutPara
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