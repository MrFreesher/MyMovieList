package com.freesher.mymovielist.register

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.freesher.mymovielist.R
import com.freesher.mymovielist.utils.login
import com.freesher.mymovielist.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            val isEmailValid = validateEmail(email)
            val isPasswordNotEmpty = checkIsPasswordNotEmpty(password)
            if (isEmailValid && isPasswordNotEmpty) {
                registerUser(email, password)
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

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    login()
                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }
    }


}