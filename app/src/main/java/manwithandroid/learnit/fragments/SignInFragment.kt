package manwithandroid.learnit.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_signin.*
import manwithandroid.learnit.R
import manwithandroid.learnit.activity.HomeActivity
import manwithandroid.learnit.helpers.UserHelper

class SignInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_signin, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            signIn()
        }

        setState(false)
    }

    private fun signIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(context, R.string.please_enter_email, Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(context, R.string.please_enter_password, Toast.LENGTH_SHORT).show()
            return
        }

        setState(true)

        UserHelper.connectUser(email, password, {
            setState(false)

            if (it.isSuccessful) {
                UserHelper.setAutoLogin(email, password)

                startActivity(Intent(context, HomeActivity::class.java))

            } else {
                UserHelper.setAutoLogin(null, null)

                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        activity?.finish()
    }

    private fun setState(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        loginButton.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
