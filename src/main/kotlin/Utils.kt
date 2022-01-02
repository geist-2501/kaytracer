import kotlin.random.Random

fun randomNum(): Double = Random.nextDouble(0.0, 1.0)

fun randomNum(min: Double, max: Double): Double = Random.nextDouble(min, max)