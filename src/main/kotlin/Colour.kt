import java.io.BufferedWriter

fun writeColour(stream: BufferedWriter, colour: Vec3, samplesPerPixel: Int) {
    var r = (256 * colour.x)
    var g = (256 * colour.y)
    var b = (256 * colour.z)

    val scale = 1.0 / samplesPerPixel
    r *= scale
    g *= scale
    b *= scale

    stream.write("${r.toInt()} ${g.toInt()} ${b.toInt()}\n")
}