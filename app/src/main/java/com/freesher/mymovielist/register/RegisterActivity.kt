package com.freesher.mymovielist.register

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.freesher.mymovielist.R
import com.freesher.mymovielist.utils.toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener {
            val email = emailEditText.text.trim()
            val password = passwordEditText.text.trim()

            val isEmailValid = validateEmail(email)
            val isPasswordNotEmpty = checkIsPasswordNotEmpty(password)
            if (isEmailValid && isPasswordNotEmpty) {
                toast("Everything is valid")
            }

        }


    }

    private fun validateEmail(email: CharSequence): Boolean {
        return if (email.isEmpty()) {
            emailEditText.error = "Empty email"
            false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = null
                true
            } else {
                emailEditText.error = "Invalid email address"
                false
            }
        }

    }


    private fun checkIsPasswordNotEmpty(password: CharSequence): Boolean {
        return if (password.isEmpty()) {
            passwordEditText.error = "Empty password"
            false
        } else {
            passwordEditText.error = null
            true
        }
    }


}