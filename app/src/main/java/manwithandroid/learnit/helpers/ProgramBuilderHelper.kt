package manwithandroid.learnit.helpers

import manwithandroid.learnit.models.*

/**
 * Created by Roi Amiel on 27/01/2018.
 */
object ProgramBuilderHelper {

    /*
    * Avilable Functions For You
    *
    * UserHelper.getConnectedUser(throwError) -> return the connected user object
    *   if throwError true' the function throw error if there is no user
    *   So use UserHelper.getConnectedUser(true)
    *
    * UserHelper.getProgramOf(class key) -> return the last program of class by key
    *
    * The user object contains fot you:
    *   classesProfiles -> map of all the profiles for each class. by <classKey: String, class profile object>
    *   userExtra -> object which contains:
    *       oldLessonsPrograms -> map of programs history for each class by <classKey: String, List<Program>>
    *       classesResults -> map of classes resualts history for each class by <classKey: String, List<ClassResualt>>
    *
    *
    * */

    fun buildFirstProgram(classKey: String, classProfile: ClassProfile, similarPrograms: List<Program>?): Program {
        UserHelper.getConnectedUser()

        //todo add content

        return Program()
    }

    fun buildProgram(classKey: String, lessonResult: LessonResult): Program {

        //todo add content

        return UserHelper.getProgramOf(classKey)
    }

    fun fitProgram(
            classKey: String,
            currentProgram: Program,
            weeksLeft: Int,
            subjectsLeft: List<Subject> = ClassesHelper.getSubjectsLeftFor(classKey)): Program {

        //todo add content

        return currentProgram
    }

}