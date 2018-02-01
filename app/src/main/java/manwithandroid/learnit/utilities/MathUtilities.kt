package manwithandroid.learnit.utilities

/**
 * Created by roi on 2/1/18.
 */
object MathUtilities {

    fun avg(numbers: List<Double>): Double {
        var avg = 0.0

        numbers.forEach { avg += it }

        return avg / numbers.size
    }

    fun avg(numbers: List<Int>): Int {
        var avg = 0

        numbers.forEach { avg += it }

        return avg / numbers.size
    }

    fun round(number: Double, mul: Int = 1): Int {
        val lowLim = (number / mul).toInt()
        val highLim = lowLim + 1

        return Math.min(
                Math.abs(number - (mul * lowLim).toDouble()),
                Math.abs(number - (mul * highLim).toDouble())
        ).toInt()
    }

}