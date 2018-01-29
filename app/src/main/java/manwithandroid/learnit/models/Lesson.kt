package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/7/18.
 */
class Lesson {

    companion object {
        const val STATE_FAILED = -1
        const val STATE_NEUTRAL = 0
        const val STATE_SUCCESSES = 1
    }

    var name: String = ""
    var description: String = ""

    var state: Int = STATE_NEUTRAL

    var classKey: String = ""

    var subjects: MutableList<Subject>? = null

    var toYear: Int = 0
    var toWeekOfYear: Int = 0

}