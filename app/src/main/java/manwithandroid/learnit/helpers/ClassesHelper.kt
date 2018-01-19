package manwithandroid.learnit.helpers

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import manwithandroid.learnit.app.LiApplication
import manwithandroid.learnit.helpers.models.EventResults
import manwithandroid.learnit.models.Class
import manwithandroid.learnit.models.Material
import manwithandroid.learnit.models.adapters.UserMaterials

/**
 * Created by roi-amiel on 1/6/18.
 */
object ClassesHelper {

    /* Finals */
    private const val CLASSES_REFERENCE_NAME = "classes"
    private const val USERS_MATERIAL_REFERENCE_NAME = "usersMaterial"
    private const val CLASSES_MATERIALS_REFERENCE_NAME = "materials"
    private const val STUDENTS_REFERENCE_NAME = "students"

    private const val MATERIALS_TAG = "materials"

    fun registerClass(classObject: Class, classUid: String) {
        val userId = UserHelper.getConnectedUserUid(true)!!

        // Add the user to the class students list
        FirebaseFirestore.getInstance()
                .collection(CLASSES_REFERENCE_NAME)
                .document(classUid)
                .collection(STUDENTS_REFERENCE_NAME)
                .document(userId)
                .set(mapOf())
                .addOnCompleteListener {
                    if (!it.isSuccessful) unregisterClass(classUid)
                }

        // Add the class to the user profile
        UserHelper.addClassToConnectedUser(classObject, {
            if (!it.isSuccessful) unregisterClass(classUid)
        })

        // Download materials
        getClassMaterial(classUid, {
            if (it == null) unregisterClass(classUid)
        })

        //todo add user finished material
    }

    private fun unregisterClass(classUid: String) {
        val uid = UserHelper.getConnectedUserUid()!!

        // Add the user to the class students list
        FirebaseFirestore.getInstance()
                .collection(CLASSES_REFERENCE_NAME)
                .document(classUid)
                .collection(STUDENTS_REFERENCE_NAME)
                .document(uid)
                .delete().addOnCompleteListener {
            if (!it.isSuccessful) throw RuntimeException("Can't unregister class from server")
        }

        // Add the class to the user profile
        UserHelper.removeClassFromConnectedUser(classUid)

        // Remove class material
        removeMaterialToCash(classUid)

        //todo remove user finished material
    }

    private fun getClassMaterial(classUid: String, onGetListener: (material: Material?) -> Unit) {
        FirebaseDatabase.getInstance()
                .getReference(CLASSES_MATERIALS_REFERENCE_NAME)
                .child(classUid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot?) {
                        if (dataSnapshot != null && dataSnapshot.exists()) {
                            val material = dataSnapshot.getValue(Material::class.java)

                            addMaterialToCash(classUid, material!!)
                            onGetListener(material)

                        } else {
                            onGetListener(null)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        onGetListener(null)
                    }
                })
    }

    fun getClass(classId: String, onGetClassListener: (eventResults: EventResults<Class>) -> Unit?) {
        FirebaseFirestore.getInstance().collection(CLASSES_REFERENCE_NAME).document(classId).get().addOnCompleteListener {
            if (it.isSuccessful && it.result.exists()) {
                val classObject = it.result.toObject(Class::class.java)
                classObject.key = classId
                onGetClassListener(EventResults(true, classObject))
            } else {
                onGetClassListener(EventResults(if (it.exception != null) it.exception?.message!! else "Can't load class"))
            }
        }
    }

    fun addMaterialToCash(classUid: String, material: Material) {
        var userMaterials = getMaterialsFromCash()
        if (userMaterials == null) {
            userMaterials = UserMaterials()
        }

        if (userMaterials.materials == null) {
            userMaterials.materials = mutableMapOf()
        }

        userMaterials.materials?.set(classUid, material)

        updateMaterialsToCash(userMaterials = userMaterials)
    }

    fun removeMaterialToCash(classUid: String) {
        val userMaterials: UserMaterials? = getMaterialsFromCash() ?: return

        if (userMaterials?.materials == null) {
            return
        }

        userMaterials.materials?.remove(classUid)

        updateMaterialsToCash(userMaterials = userMaterials)
    }

    /* User material DataTag functions */

    fun updateMaterialsToCash(currentUserUid: String = UserHelper.getConnectedUserUid()!!, userMaterials: UserMaterials) =
            updateDataTagToCash(currentUserUid, MATERIALS_TAG, userMaterials)

    fun getMaterialsFromCash(currentUserUid: String = UserHelper.getConnectedUserUid()!!): UserMaterials? =
            getDataTagFromCash(currentUserUid, MATERIALS_TAG)

    fun clearUnusedMaterialsFromCash(currentUserUid: String = UserHelper.getConnectedUserUid()!!) =
            clearUnusedDataTagsFromCash(currentUserUid, MATERIALS_TAG)

    /* Data tags functions */

    private fun updateDataTagToCash(currentUserUid: String = UserHelper.getConnectedUserUid()!!, dataTag: String, classObject: Any) =
            LiApplication.pref.edit().putString("$dataTag/$currentUserUid",
                    MapperHelper.toJson(classObject).toString()).apply()

    private inline fun <reified T> getDataTagFromCash(currentUserUid: String = UserHelper.getConnectedUserUid()!!, dataTag: String): T? =
            MapperHelper.fromJson(
                    LiApplication.pref.getString(
                            "$dataTag/$currentUserUid", null),
                    T::class.java)

    private fun clearUnusedDataTagsFromCash(currentUserUid: String = UserHelper.getConnectedUserUid()!!, dataTag: String) =
            LiApplication.pref.all.keys
                    .filter { it.startsWith(dataTag) && !it.endsWith(currentUserUid) }
                    .forEach { LiApplication.pref.edit().remove(it).apply() }
}