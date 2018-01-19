package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/6/18.
 */
class Question {

    companion object {
        val TYPE_CHOICE = 0
        val TYPE_OPEN = 1
        val TYPE_OPEN_SPECIFIC = 2
    }

    var question: String = ""
    var description: String = ""
    var hint: String = ""

    var type: Int = TYPE_CHOICE

    var answers: MutableList<MutableList<String>?>? = null
}