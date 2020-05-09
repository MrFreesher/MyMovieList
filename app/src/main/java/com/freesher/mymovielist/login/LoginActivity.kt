package com.freesher.mymovielist.login

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.freesher.mymovielist.R
import com.freesher.mymovielist.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val isEmailValid = validateEmail(email)
            val isPasswordNotEmpty = checkIsPasswordNotEmpty(password)
            if (isEmailValid && isPasswordNotEmpty) {
                //   registerUser(email, password)
                toast("Everything is valid")
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            emailEditText.error = getString(R.string.empty_email_label)
            false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = null
                true
            } else {
                emailEditText.error = getString(R.string.invalid_email_label)
                false
            }
        }

    }


    private fun checkIsPasswordNotEmpty(password: String): Boolean {
        return if (password.isEmpty()) {
            passwordEditText.error = getString(R.string.empty_password_label)
            false
        } else {
            passwordEditText.error = null
            true
        }
    }
}
