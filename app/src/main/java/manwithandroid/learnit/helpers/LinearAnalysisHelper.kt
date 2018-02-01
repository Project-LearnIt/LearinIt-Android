package manwithandroid.learnit.helpers

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import manwithandroid.learnit.utilities.MathUtilities

/**
 * Created by roi on 2/1/18.
 */
object LinearAnalysisHelper {

    private const val PARAMETER_LAST_TAG = "_linear_anls_last_param"
    private const val PARAMETER_AVG_TAG = "_linear_anls_avg_param"
    private const val PARAMETER_HIGH_POINT_TAG = "_linear_anls_hp_param"

    fun linearDifAnalysis(numbers: List<Double>, values: List<Double>, name: String): Double {
        val resualts = mutableListOf<LinearResualt>()

        // last
        resualts += analyzeLast(
                numbers,
                values,
                FirebaseRemoteConfig.getInstance().getDouble(name + PARAMETER_LAST_TAG)
        )

        // total avg
        resualts += analyzeAvg(
                numbers,
                values,
                FirebaseRemoteConfig.getInstance().getDouble(name + PARAMETER_AVG_TAG)
        )

        // High Point
        resualts += analyzeHighPoint(
                numbers,
                values,
                FirebaseRemoteConfig.getInstance().getDouble(name + PARAMETER_HIGH_POINT_TAG)
        )

        var size = 0
        var avg = 0.0

        resualts.forEach {
            size += it.weight
            avg += it.value * it.weight
        }

        return avg / size
    }

    private fun analyzeLast(numbers: List<Double>, values: List<Double>, parameter: Double) =
            LinearResualt(
                    getWeight(numbers.subList(numbers.size - 2, numbers.size - 1), parameter),
                    values[values.size - 1] - values[values.size - 2]
            )

    private fun analyzeAvg(numbers: List<Double>, values: List<Double>, parameter: Double) =
            LinearResualt(
                    getWeight(numbers, parameter),
                    values.sum() / numbers.size
            )

    private fun analyzeHighPoint(numbers: List<Double>, values: List<Double>, parameter: Double) =
            LinearResualt(MathUtilities.round(parameter), values[numbers.indexOf(numbers.max())])

    private fun getWeight(numbers: List<Double>, parameter: Double) =
            MathUtilities.round(Math.max((0..(numbers.size - 1)).sumByDouble { numbers[it + 1] - numbers[it] } / (parameter * numbers.size), 0.0))

    class LinearResualt(var weight: Int, var value: Double)
}
