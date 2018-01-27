package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/6/18.
 */
class Media {

    companion object {
        /* Levels */
        const val LEVEL_EASY = 0
        const val LEVEL_NORMAL = 1
        const val LEVEL_HARD = 2

        /* Types */
        const val TYPE_TEXT = 0
        const val TYPE_VIDEO = 1
        const val TYPE_SOUND = 2
        const val TYPE_IMAGE = 3
    }

    var level: Int = LEVEL_EASY
    var type: Int = TYPE_TEXT

    var data: String = ""

    var extras: MutableList<MediaExtra>? = null

    var questions: MutableList<Question>? = null

}
