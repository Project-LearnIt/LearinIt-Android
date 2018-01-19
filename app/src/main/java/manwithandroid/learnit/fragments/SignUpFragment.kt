package manwithandroid.learnit.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup.*
import manwithandroid.learnit.R
import manwithandroid.learnit.activity.HomeActivity
import manwithandroid.learnit.helpers.UserHelper
import manwithandroid.learnit.models.User

/**
 * Created by roi-amiel on 12/28/17.
 */

class SignUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpButton.setOnClickListener {
            signUp()
        }

        setState(false)
    }

    private fun signUp() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val repassword = repasswordEditText.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(context, R.string.please_enter_name, Toast.LENGTH_SHORT).show()
            return
        }

        if (email.isEmpty()) {
            Toast.makeText(context, R.string.please_enter_email, Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(context, R.string.please_enter_password, Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 4) {
            Toast.makeText(context, R.string.password_too_short, Toast.LENGTH_SHORT).show()
            return
        }

        if (password != repassword) {
            Toast.makeText(context, R.string.passwords_not_match, Toast.LENGTH_SHORT).show()
            return
        }

        setState(true)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            val uid = UserHelper.getConnectedUserUid()

            if (task.isSuccessful && uid != null) {
                UserHelper.createUser(User(name, email), uid, {
                    setState(false)

                    if (it.isSuccessful) {
                        UserHelper.setAutoLogin(email, password)

                        startActivity(Intent(context, HomeActivity::class.java))
                        activity?.finish()

                    } else {
                        UserHelper.setAutoLogin(null, null)

                        FirebaseAuth.getInstance().currentUser?.delete()
                        throw RuntimeException("Can't create child to the user in the database: ${it.message}")
                    }
                })

            } else {
                setState(false)

                UserHelper.setAutoLogin(null, null)

                Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setState(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        signUpButton.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

}
