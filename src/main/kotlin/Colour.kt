import java.io.BufferedWriter
import kotlin.math.sqrt

fun writeColour(stream: BufferedWriter, colour: Vec3, samplesPerPixel: Int) {
    var r = colour.x
    var g = colour.y
    var b = colour.z

    val scale = 1.0 / samplesPerPixel
    r = sqrt(scale * r)
    g = sqrt(scale * g)
    b = sqrt(scale * b)

    stream.write("" +
            "${(256.0 * r.coerceIn(0.0, 0.999)).toInt()} " +
            "${(256.0 * g.coerceIn(0.0, 0.999)).toInt()} " +
            "${(256.0 * b.coerceIn(0.0, 0.999)).toInt()}\n")
}