package manwithandroid.learnit.helpers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import manwithandroid.learnit.app.LiApplication
import manwithandroid.learnit.models.Class
import manwithandroid.learnit.utilities.TimeUtilities

/**
 * Created by Roi Amiel on 18/01/2018.
 */
object LessonsBuilderHelper {

    /* Finals */
    private const val ALREADY_SET_ALARM_TAG = "AlreadySetBuildTaskAlarm"

    private const val BUILD_TASK_CODE = 3345
    private const val INTERVAL_WEEK = AlarmManager.INTERVAL_DAY * 7

    /* Lessons build task */
    fun initBuildTask(force: Boolean = false, time: Long = TimeUtilities.getNextFirstDay()) {
        if (force || !LiApplication.pref.getBoolean(ALREADY_SET_ALARM_TAG, false)) {
            val alarmManager = LiApplication.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val pendingIntent = PendingIntent.getBroadcast(LiApplication.context,
                    BUILD_TASK_CODE, Intent(LiApplication.context, LessonsBuildTaskReceiver::class.java), PendingIntent.FLAG_CANCEL_CURRENT)

            alarmManager.cancel(pendingIntent)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, INTERVAL_WEEK, pendingIntent)

            LiApplication.pref.edit().putBoolean(ALREADY_SET_ALARM_TAG, true).apply()
        }
    }

    fun updateBuildTask() {
        if (!UserHelper.isConnectedUser()) return

        if (UserHelper.getConnectedUser()?.lastLessonsBuildTask == null) return

        if (UserHelper.getConnectedUser()?.lastLessonsBuildTask?.time!! + INTERVAL_WEEK < TimeUtilities.currentClearTimeMillis()) {
            initBuildTask(true, TimeUtilities.getNextFirstDay(UserHelper.getConnectedUser()?.lastLessonsBuildTask?.time!!))
            buildTask()
        }
    }

    private fun buildTask(classes: List<Class>? = UserHelper.getConnectedUser()?.classes) {
        UserHelper.updateBuildTaskTime()

        FirebaseDatabase.getInstance().getReference("testAlarm").push().setValue("\"build task run ${System.currentTimeMillis()}\" by ${UserHelper
                .getConnectedUser()?.name}")
    }

    class LessonsBuildTaskReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            buildTask()
        }
    }
}