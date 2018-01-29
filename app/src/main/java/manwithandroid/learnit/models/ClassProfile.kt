package manwithandroid.learnit.models

/**
 * Created by Roi Amiel on 20/01/2018.
 */

class ClassProfile {

    companion object {

        const val LOVE_RATE_VERY_LOVE = 0
        const val LOVE_RATE_LOVE = 1
        const val LOVE_RATE_FIFTY_FIFTY = 2
        const val LOVE_RATE_DONT_LOVE = 3
        const val LOVE_RATE_HATE = 4

        const val LESSON_DURATION_VERY_LONG = 0
        const val LESSON_DURATION_LONG = 1
        const val LESSON_DURATION_MEDIUM = 2
        const val LESSON_DURATION_SHORT = 3
        const val LESSON_DURATION_VERY_SHORT = 4

        const val GOOD_RATE_VERY_GOOD = 0
        const val GOOD_RATE_GOOD = 1
        const val GOOD_RATE_FIFTY_FIFTY = 2
        const val GOOD_RATE_DONT_GOOD = 3
        const val GOOD_RATE_TERRIBLE = 4

    }

    var loveRate: Int = LOVE_RATE_VERY_LOVE
    var lessonDuration: Int = LESSON_DURATION_VERY_LONG
    var timesInWeek: Int = 1
    var goodRate: Int = GOOD_RATE_VERY_GOOD
    var textsPercent: Int = 10
    var videosPercent: Int = 10
    var simulationPercent: Int = 10

}
