import java.io.BufferedWriter

fun writeColour(stream: BufferedWriter, colour: Vec3) {
    val r = (256 * colour.x).toInt()
    val g = (256 * colour.y).toInt()
    val b = (256 * colour.z).toInt()
    stream.write("$r $g $b\n")
}