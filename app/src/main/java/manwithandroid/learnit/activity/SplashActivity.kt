package manwithandroid.learnit.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import manwithandroid.learnit.BuildConfig
import manwithandroid.learnit.R
import manwithandroid.learnit.app.LiApplication
import manwithandroid.learnit.helpers.UserHelper
import manwithandroid.learnit.utilities.TimeUtilities

class SplashActivity : AppCompatActivity() {

    private val minLoadingTime = if (BuildConfig.DEBUG) 0 else FirebaseRemoteConfig.getInstance().getLong("loading_min_time")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null && UserHelper.getConnectedUser() != null) {
            UserHelper.getConnectedUser({})

            startActivity(Intent(this, HomeActivity::class.java))
            return
        }

        setContentView(R.layout.activity_splash)

        load()
    }

    private fun load() {
        val email = LiApplication.getLastEmail()
        val password = LiApplication.getLastPassword()

        val startTime = System.currentTimeMillis()

        val splashThread = Thread({
            TimeUtilities.sleep(minLoadingTime - System.currentTimeMillis() + startTime)
            startActivity(Intent(this, SignInActivity::class.java))
        })

        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
            UserHelper.connectUser(email, password, {
                if (System.currentTimeMillis() - startTime < minLoadingTime) {
                    TimeUtilities.sleep(minLoadingTime - System.currentTimeMillis() + startTime)
                }

                if (!it.isSuccessful) startActivity(Intent(baseContext, SignInActivity::class.java))
                else startActivity(Intent(baseContext, HomeActivity::class.java))
            })

        } else {
            splashThread.start()
        }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        finish()
    }
}
