package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/7/18.
 */
class Lesson {

    var name: String = ""
    var description: String = ""

    var classId: String = ""

    var subjects: MutableList<Subject>? = null

    var toYear: Int = 0
    var toWeekOfYear: Int = 0

}