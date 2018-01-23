package manwithandroid.learnit.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_user_profile.*
import manwithandroid.learnit.R
import manwithandroid.learnit.models.UserProfile
import manwithandroid.learnit.utilities.TimeUtilities
import java.util.*

/**
 * Created by roi-amiel on 1/10/18.
 */
// todo make generic
class CreateUserProfileActivity : AppCompatActivity(), OnDateSetListener {

    private var lastCalender: Calendar = Calendar.getInstance(TimeZone.getDefault())

    private var valueReturned = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_profile)

        supportActionBar?.title = resources.getString(R.string.create_user_profile_title)

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
            val profile = createUserProfile()

            if (profile != null) {
                valueReturned = true
                createUserProfileListener(profile)

                finish()
            }
        }
    }

    private fun createUserProfile(): UserProfile? {
        val birthday = birthdayEditText.text.toString()
        val favoriteMovieGenre = favoriteMovieSpinner.selectedItemPosition
        val numberOfBrothers = brothersNumberSpinner.selectedItemPosition
        val gender = if (genderSwitch.isChecked) UserProfile.GENDER_MALE else UserProfile.GENDER_FEMALE
        val favoriteLesson = favoriteLessonSpinner.selectedItemPosition

        if (birthday.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_birtday, Toast.LENGTH_SHORT).show()
            return null
        }

        val userProfile = UserProfile()
        userProfile.favoriteMovieGenre = favoriteMovieGenre
        userProfile.numberOfBrothers = numberOfBrothers
        userProfile.gender = gender
        userProfile.birthday = birthday
        userProfile.favoriteLesson = favoriteLesson

        return userProfile
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        lastCalender.set(Calendar.YEAR, year)
        lastCalender.set(Calendar.MONTH, month)
        lastCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        birthdayEditText.setText(TimeUtilities.dateFormat.format(lastCalender.time))
    }

    override fun onDestroy() {
        if (!valueReturned) {
            createUserProfileListener(null)
        }

        super.onDestroy()
    }

    companion object {
        private lateinit var createUserProfileListener: (userProfile: UserProfile?) -> Unit

        fun createIntent(context: Context, onCreateUserProfileListener: (userProfile: UserProfile?) -> Unit): Intent {
            createUserProfileListener = onCreateUserProfileListener

            return Intent(context, CreateUserProfileActivity::class.java)
        }
    }
}
