package com.freesher.mymovielist.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.freesher.mymovielist.HomeActivity
import com.freesher.mymovielist.R
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


    private fun checkIsPasswordNotEmpty(password: String): Boolean {
        return if (password.isEmpty()) {
            passwordEditText.error = "Empty password"
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
                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }
    }


}