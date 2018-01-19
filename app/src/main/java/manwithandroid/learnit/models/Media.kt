package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/6/18.
 */
class Media {

    companion object {
        /* Levels */
        val LEVEL_EASY = 0
        val LEVEL_NORMAL = 1
        val LEVEL_HARD = 2

        /* Types */
        val TYPE_TEXT = 0
        val TYPE_VIDEO = 1
        val TYPE_SOUND = 2
        val TYPE_IMAGE = 3
    }

    var level: Int = LEVEL_EASY
    var type: Int = TYPE_TEXT

    var data: String = ""

    var extras: MutableList<MediaExtra>? = null

    var questions: MutableList<Question>? = null

}
