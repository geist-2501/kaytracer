import java.io.File

fun main(args: Array<String>) {

    val f = File("img.ppm").bufferedWriter()

    val width = 256
    val height = 256

    f.write("P3\n")
    f.write("$width $height\n")
    f.write("255\n")

    for (j in height - 1 downTo 0) {
        for (i in 0 until width) {
            val r = i / (width).toDouble()
            val g = j / (height).toDouble()
            val b = 0.25

            val ir = (256f * r).toInt()
            val ig = (256f * g).toInt()
            val ib = (256f * b).toInt()

            f.write("$ir $ig $ib\n")
        }
    }

    f.close()

    println("Done!")
}