package manwithandroid.learnit.models

import com.google.firebase.firestore.Exclude
import java.util.*

/**
 * Created by roi-amiel on 1/5/18.
 */
class User {

    var name: String = ""
    var email: String = ""

    @Exclude
    var userExtra: UserExtra? = null

    var lastLessonsBuildTask: Date? = null

    var userProfile: UserProfile? = null

    var weekLessons: MutableList<Lesson>? = null

    var uncompletedLessons: MutableList<Lesson>? = null

    var classes: MutableList<Class>? = null

    // currentPrograms map by class key and the program of this class
    var currentPrograms: MutableMap<String, Program>? = null

    // completed subjects map by class key and the list of the indexes of the completed subjects
    var completedSubjects: MutableMap<String, MutableList<Int>>? = null

    // Lessons profiles map by class key and lesson profile
    var classesProfiles: MutableMap<String, ClassProfile>? = null

    constructor() {
        //do nothing
    }

    constructor(Name: String, Email: String) {
        this.name = Name
        this.email = Email
    }
}
