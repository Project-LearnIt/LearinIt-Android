package manwithandroid.learnit.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_create_lesson_profile.*
import manwithandroid.learnit.R
import manwithandroid.learnit.models.LessonProfile

/**
 * Created by Roi Amiel on 20/01/2018.
 */
class CreateLessonProfileActivity : AppCompatActivity() {

    private val maximumPercent = FirebaseRemoteConfig.getInstance().getLong("lesson_profile_max_percent").toInt()
    private val minimumPercent = FirebaseRemoteConfig.getInstance().getLong("lesson_profile_min_percent").toInt()

    private var valueReturned = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lesson_profile)

        supportActionBar?.title = resources.getString(R.string.create_lesson_profile_title)

        textsPercentSeekBar.max = maximumPercent
        videosPercentSeekBar.max = maximumPercent
        simulationPercentSeekBar.max = maximumPercent

        textsPercentSeekBar.min = minimumPercent
        videosPercentSeekBar.min = minimumPercent
        simulationPercentSeekBar.min = minimumPercent

        continueButton.setOnClickListener {
            val profile = createLessonProfile()

            valueReturned = true
            createLessonProfileListener(profile)

            finish()
        }
    }

    private fun createLessonProfile(): LessonProfile {
        val loveRate = loveRateSpinner.selectedItemPosition
        val lessonDuration = lessonDurationSpinner.selectedItemPosition
        val timesInWeek = timesInWeekSpinner.selectedItemPosition
        val goodRate = goodRateSpinner.selectedItemPosition
        val textsPercent = textsPercentSeekBar.progress
        val videosPercent = videosPercentSeekBar.progress
        val simulationPercent = simulationPercentSeekBar.progress

        val lessonProfile = LessonProfile()
        lessonProfile.loveRate = loveRate
        lessonProfile.lessonDuration = lessonDuration
        lessonProfile.timesInWeek = timesInWeek
        lessonProfile.goodRate = goodRate
        lessonProfile.textsPercent = textsPercent
        lessonProfile.videosPercent = videosPercent
        lessonProfile.simulationPercent = simulationPercent

        return lessonProfile
    }

    override fun onDestroy() {
        if (!valueReturned) {
            createLessonProfileListener(null)
        }

        super.onDestroy()
    }

    companion object {
        private lateinit var createLessonProfileListener: (lessonProfile: LessonProfile?) -> Unit

        fun createIntent(context: Context, onCreateProfileListener: (lessonProfile: LessonProfile?) -> Unit): Intent {
            createLessonProfileListener = onCreateProfileListener

            return Intent(context, CreateLessonProfileActivity::class.java)
        }
    }

}