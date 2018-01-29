package manwithandroid.learnit.models

/**
 * Created by roi on 1/27/18.
 */
class LessonResult {

    var grade: Int = 0

    var program: Program? = null

    var media: List<MediaResult>? = null
    var questions: List<QuestionResult>? = null

    var breaks: List<Int>? = null

    // Without breaks
    var time: Int = 0

}