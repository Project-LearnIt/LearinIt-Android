package manwithandroid.learnit.helpers

import manwithandroid.learnit.helpers.LinearAnalysisHelper.linearDifAnalysis
import manwithandroid.learnit.models.ClassProfile
import manwithandroid.learnit.models.LessonResult
import manwithandroid.learnit.models.Program
import manwithandroid.learnit.models.Subject

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

        // todo Get params
        val currentProgram = UserHelper.getProgramOf(classKey)

        var programsHistory = UserHelper.getConnectedUser(true)?.userExtra?.oldLessonsPrograms!![classKey]!!
        programsHistory = MutableList(programsHistory.size, { programsHistory[it] })

        programsHistory.add(currentProgram)

        // todo analys data
        //linearDifAnalysis()


        // todo build program

        // todo log

        return currentProgram
    }

    fun fitProgram(
            classKey: String,
            currentProgram: Program,
            weeksLeft: Int,
            subjectsLeft: List<Subject> = ClassesHelper.getSubjectsLeftFor(classKey)): Program {

        // !!! do small changes to the program !!!

        //todo add content

        return currentProgram
    }

}