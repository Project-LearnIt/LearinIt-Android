package manwithandroid.learnit.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_profile.*
import manwithandroid.learnit.R
import manwithandroid.learnit.models.Profile
import manwithandroid.learnit.utilities.TimeUtilities
import java.util.*

/**
 * Created by roi-amiel on 1/10/18.
 */
// todo make generic
class CreateProfileActivity : AppCompatActivity(), OnDateSetListener {

    private var lastCalender: Calendar = Calendar.getInstance(TimeZone.getDefault())

    private var valueReturned = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        birthdayEditText.setOnClickListener({
            DatePickerDialog(
                    this,
                    this,
                    lastCalender.get(Calendar.YEAR),
                    lastCalender.get(Calendar.MONTH),
                    lastCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        })

        continueButton.setOnClickListener {
            val profile = createProfile()

            if (profile != null) {
                valueReturned = true
                createProfileListener(profile)

                finish()
            }
        }
    }

    private fun createProfile(): Profile? {
        val birthday = birthdayEditText.text.toString()
        val favoriteMovieGenre = favoriteMovieSpinner.selectedItemPosition
        val numberOfBrothers = brothersNumberSpinner.selectedItemPosition
        val gender = if (genderSwitch.isChecked) Profile.GENDER_MALE else Profile.GENDER_FEMALE
        val favoriteLesson = favoriteLessonSpinner.selectedItemPosition

        if (birthday.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_birtday, Toast.LENGTH_SHORT).show()
            return null
        }

        val profile = Profile()
        profile.favoriteMovieGenre = favoriteMovieGenre
        profile.numberOfBrothers = numberOfBrothers
        profile.gender = gender
        profile.birthday = birthday
        profile.favoriteLesson = favoriteLesson

        return profile
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        lastCalender.set(Calendar.YEAR, year)
        lastCalender.set(Calendar.MONTH, month)
        lastCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        birthdayEditText.setText(TimeUtilities.dateFormat.format(lastCalender.time))
    }

    override fun onDestroy() {
        if (!valueReturned) {
            createProfileListener(null)
        }

        super.onDestroy()
    }

    companion object {
        private lateinit var createProfileListener: (profile: Profile?) -> Unit

        fun createIntent(context: Context, onCreateProfileListener: (profile: Profile?) -> Unit): Intent {
            createProfileListener = onCreateProfileListener

            return Intent(context, CreateProfileActivity::class.java)
        }
    }
}
