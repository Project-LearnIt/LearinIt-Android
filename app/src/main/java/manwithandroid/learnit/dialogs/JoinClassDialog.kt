package manwithandroid.learnit.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatDialog
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dilaog_join_class.*
import manwithandroid.learnit.R
import manwithandroid.learnit.activity.CreateLessonProfileActivity
import manwithandroid.learnit.helpers.ClassesHelper
import manwithandroid.learnit.helpers.UserHelper

/**
 * Created by roi-amiel on 1/6/18.
 */
class JoinClassDialog(context: Context) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dilaog_join_class)

        setState(false)

        joinButton.setOnClickListener {
            joinClass()
        }
    }

    private fun joinClass() {
        val classId = classIdEditText.text.toString()

        if (classId.isEmpty()) {
            Toast.makeText(context, R.string.enter_class_id, Toast.LENGTH_SHORT).show()
            return
        }

        if (UserHelper.isConnectedUserInClass(classId)) {
            Toast.makeText(context, R.string.you_alredy_in_this_class, Toast.LENGTH_SHORT).show()
            return
        }

        setState(true)
        ClassesHelper.getClass(classId, {
            setState(false)
            if (it.isSuccessful) {
                val classObject = it.resultObject
                context.startActivity(CreateLessonProfileActivity.createIntent(context, {
                    if (it == null) Toast.makeText(context, R.string.you_must_create_lesson_profile, Toast.LENGTH_SHORT).show()
                    else ClassesHelper.registerClass(classObject!!, it, classId)

                    cancel()
                }))
            } else {
                Toast.makeText(context, R.string.class_not_found, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setState(loading: Boolean) {
        joinButton.visibility = if (loading) View.GONE else View.VISIBLE
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        setCancelable(!loading)
    }
}
