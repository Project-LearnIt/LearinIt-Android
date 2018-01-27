package manwithandroid.learnit.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import manwithandroid.learnit.R
import manwithandroid.learnit.helpers.MapperHelper
import manwithandroid.learnit.models.Lesson

/**
 * Created by roi-amiel on 12/28/17.
 */
class LessonActivity : AppCompatActivity() {

    private lateinit var closeWarningDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        val lesson = getExtraLesson(intent)

        supportActionBar?.title = lesson?.name

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()

        closeWarningDialog = AlertDialog.Builder(this)
                .setTitle(R.string.exit_warning)
                .setMessage(R.string.are_you_sure_you_want_to_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                    super.onBackPressed()
                })
                .setNegativeButton(R.string.no, { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                })
                .create()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return true
    }

    override fun onBackPressed() {
        if (!closeWarningDialog.isShowing) {
            closeWarningDialog.show()
        }
    }

    companion object {
        private const val EXTRA_LESSON_TAG = "lesson"

        fun createIntent(context: Context, lesson: Lesson): Intent {
            val intent = Intent(context, LessonActivity::class.java)

            intent.putExtra(EXTRA_LESSON_TAG, MapperHelper.toJson(lesson).toString())

            return intent
        }

        private fun getExtraLesson(intent: Intent) =
                MapperHelper.fromJson(intent.getStringExtra(EXTRA_LESSON_TAG), Lesson::class.java)
    }

}
