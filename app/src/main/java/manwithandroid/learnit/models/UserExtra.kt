package manwithandroid.learnit.models

import com.google.firebase.database.Exclude

/**
 * Created by roi on 1/29/18.
 */
class UserExtra {

    @Exclude
    private var valueChanged = false

    // Map of all classes (bey key) and thier currentPrograms history
    var oldLessonsPrograms: MutableMap<String, List<Program>>? = null
        get() {
            valueChanged = true
            return field
        }

    // Map of all classes (bey key) and thier lessons resualts
    var classesResults: MutableMap<String, List<LessonResult>>? = null
        get() {
            valueChanged = true
            return field
        }

    fun hasChanged() = valueChanged

}