package manwithandroid.learnit.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import manwithandroid.learnit.R
import manwithandroid.learnit.helpers.LessonsBuilderHelper

class LiApplication : Application() {

    companion object {
        private const val LAST_EMAIL_TAG = "Email"
        private const val LAST_PASSWORD_TAG = "Password"

        private var installed = false

        lateinit var context: Context
        lateinit var self: LiApplication
        lateinit var pref: SharedPreferences

        fun installApplication(context: Context) {
            if (installed) return

            this.context = context

            pref = context.getSharedPreferences("LiApplication", Context.MODE_PRIVATE)

            FirebaseRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults)

            installed = true
        }

        fun getLastEmail(): String? = pref.getString(LAST_EMAIL_TAG, null)
        fun getLastPassword(): String? = pref.getString(LAST_PASSWORD_TAG, null)

        fun setLastEmail(lastEmail: String?) = pref.edit().putString(LAST_EMAIL_TAG, lastEmail).apply()
        fun setLastPassword(lastPassword: String?) = pref.edit().putString(LAST_PASSWORD_TAG, lastPassword).apply()
    }


    override fun onCreate() {
        super.onCreate()

        self = this

        installApplication(this)

        LessonsBuilderHelper.initBuildTask()
    }
}
