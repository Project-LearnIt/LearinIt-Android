package manwithandroid.learnit.helpers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import manwithandroid.learnit.app.LiApplication
import manwithandroid.learnit.helpers.models.EventResults
import manwithandroid.learnit.models.*
import manwithandroid.learnit.utilities.TimeUtilities
import java.lang.RuntimeException
import java.util.*

/**
 * Created by roi-amiel on 1/5/18.
 */
object UserHelper {

    /* Finals */
    private const val USERS_REFERENCE_NAME = "users"
    private const val USERS_EXTRA_REFERENCE_NAME = "usersExtra"

    private var connectedUser: User? = null

    val authListener = FirebaseAuth.getInstance().addAuthStateListener {
        if (it.currentUser != null) {
            getConnectedUser({})

            if (it.currentUser?.uid == null) throw RuntimeException("User uid is null after auth change")

            LessonsBuilderHelper.updateBuildTask()

            ClassesHelper.clearUnusedMaterialsFromCash(it.currentUser?.uid!!)
        } else {
            connectedUser = null
        }
    }

    fun getUserExtra(onFinish: (eventResults: EventResults<UserExtra>) -> Unit) {
        FirebaseFirestore.getInstance().collection(USERS_EXTRA_REFERENCE_NAME).document(getConnectedUserUid(true)!!).get().addOnCompleteListener({
            if (!it.isSuccessful || !it.result.exists()) {
                onFinish(EventResults(it.exception?.message))
            } else {
                val userExtra = it.result.toObject(UserExtra::class.java)

                connectedUser?.userExtra = userExtra

                onFinish(EventResults(userExtra))
            }
        })
    }

    fun updateUserExtra(onFinish: (eventResults: EventResults<Any>) -> Unit) {
        val user = getConnectedUser(true)!!

        if (user.userExtra == null || !user.userExtra!!.hasChanged()) return

        FirebaseFirestore.getInstance().collection(USERS_EXTRA_REFERENCE_NAME).document(getConnectedUserUid(true)!!).set(user.userExtra!!).addOnCompleteListener {
            onFinish(EventResults(it.isSuccessful))
        }
    }

    fun getConnectedUser(throwError: Boolean = false) = if (connectedUser == null && throwError) throw IllegalStateException("There is no connected user") else connectedUser

    fun isConnectedUser() = (connectedUser != null) && (getConnectedUserUid() != null)

    fun getConnectedUserUid(throwError: Boolean = false) = FirebaseAuth.getInstance().currentUser?.uid
            ?: if (throwError) throw RuntimeException("There is no connected user") else null

    fun getProgramOf(classKey: String): Program {
        return getConnectedUser(true)!!.currentPrograms?.get(classKey)!!
    }

    fun updateProgramOf(classKey: String, program: Program, updateToServer: Boolean = true) {
        val connectedUser = getConnectedUser(true)!!

        if (connectedUser.currentPrograms == null) {
            connectedUser.currentPrograms = mutableMapOf()
        }

        connectedUser.currentPrograms!![classKey] = program

        if (updateToServer) updateUser {}

        // todo add to program history
    }

    fun getUsedSubjectsOf(classKey: String) =
            getConnectedUser(true)!!.completedSubjects?.get(classKey)

    fun setUsedSubjects(classKey: String, usedIndexs: List<Int>) {
        val connectedUser = getConnectedUser(true)!!

        if (connectedUser.completedSubjects == null) {
            connectedUser.completedSubjects = mutableMapOf()
        }

        if (connectedUser.completedSubjects!![classKey] == null) {
            connectedUser.completedSubjects!![classKey] = mutableListOf()
        }

        connectedUser.completedSubjects!![classKey]!!.addAll(usedIndexs)

        UserHelper.updateUser {

        }
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()

        setAutoLogin(null, null)
    }

    fun setAutoLogin(email: String?, password: String?) {
        LiApplication.setLastEmail(email)
        LiApplication.setLastPassword(password)
    }

    fun updateLastBuildTaskTime(date: Date = Date(TimeUtilities.currentClearTimeMillis()), updateToServer: Boolean = false) {
        connectedUser?.lastLessonsBuildTask = date

        // Update the user if needs to
        if (updateToServer) updateUser {}
    }

    fun isConnectedUserInClass(classUid: String): Boolean {
        if (connectedUser == null) throw IllegalStateException("Connected user can't be null")

        if (connectedUser?.classes == null) return false

        connectedUser?.classes?.forEach { if (it.key == classUid) return true }

        return false
    }

    fun addClassToConnectedUser(classObject: Class, classProfile: ClassProfile, addToClassListener: (eventResults: EventResults<Any>) -> Unit) {
        if (connectedUser == null) throw IllegalStateException("The connected user object is null")

        if (connectedUser?.classes == null) connectedUser?.classes = mutableListOf()
        if (connectedUser?.classesProfiles == null) connectedUser?.classesProfiles = mutableMapOf()

        connectedUser?.classes!!.add(classObject)
        connectedUser?.classesProfiles!![classObject.key] = classProfile

        // Build first program
        updateProgramOf(classObject.key,
                ProgramBuilderHelper.buildFirstProgram(classObject.key, classProfile, null /* todo get form server */),
                false)

        updateUser({
            if (it.isSuccessful) {
                addToClassListener(EventResults(true))

            } else {
                connectedUser?.classes!!.remove(classObject)
                connectedUser?.classesProfiles!!.remove(classObject.key)

                addToClassListener(EventResults(it.message))
            }
        })
    }

    fun removeClassFromConnectedUser(classKey: String) {
        if (connectedUser == null) throw IllegalStateException("The connected user object is null")

        if (connectedUser?.classes == null) return

        connectedUser?.classes?.filter { it.key == classKey }?.forEach { connectedUser?.classes?.remove(it) }

        updateUser({
            if (!it.isSuccessful) throw RuntimeException("Can't remove class from user at server")
        })
    }

    fun updateUser(updateUserListener: (eventResults: EventResults<User>) -> Unit?) {
        val userId = UserHelper.getConnectedUserUid(true)!!

        connectedUser?.let {
            FirebaseFirestore.getInstance().collection(USERS_REFERENCE_NAME).document(userId).set(it).addOnCompleteListener {
                if (!it.isSuccessful || !it.isComplete) {
                    val errorMessage = "Can't update this (${connectedUser.toString()}) user"
                    updateUserListener(EventResults(errorMessage))
                    throw RuntimeException(errorMessage)

                } else {
                    updateUserListener(EventResults(true))

                    updateUserExtra {}
                }
            }
        }
    }

    fun createUser(user: User, uid: String, onCompleteListener: (eventResults: EventResults<User>) -> Unit?) {
        FirebaseFirestore.getInstance().collection(USERS_REFERENCE_NAME).document(uid).set(user).addOnCompleteListener {
            if (it.isSuccessful) connectedUser = user

            onCompleteListener(EventResults(it.isSuccessful, connectedUser))
        }
    }

    fun connectUser(email: String? = LiApplication.getLastEmail(),
                    password: String? = LiApplication.getLastPassword(),
                    loginListener: (eventResults: EventResults<User>) -> Unit?) {

        if (email == null || password == null) return

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                LiApplication.setLastEmail(email)
                LiApplication.setLastPassword(password)

                getConnectedUser({
                    loginListener(it)
                })
            } else {
                loginListener(EventResults(it.exception?.message!!))
            }
        }
    }

    fun getConnectedUser(getUserListener: (eventResults: EventResults<User>) -> Unit?) {
        FirebaseFirestore.getInstance().collection(USERS_REFERENCE_NAME).document(UserHelper.getConnectedUserUid(true)!!).get().addOnCompleteListener {
            val snapshot = it.result

            if (it.isSuccessful && snapshot != null && snapshot.exists()) {
                connectedUser = snapshot.toObject(User::class.java)

                if (connectedUser != null) {
                    getUserListener(EventResults(true, connectedUser))
                } else {
                    getUserListener(EventResults("User from server is null"))
                }

                getUserExtra {

                }
            } else {
                getUserListener(EventResults(if (it.exception != null) it.exception?.message.toString() else "Unknown error"))
            }
        }
    }
}