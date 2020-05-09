package com.freesher.mymovielist.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.freesher.mymovielist.HomeActivity
import com.freesher.mymovielist.R
import com.freesher.mymovielist.register.RegisterActivity
import com.freesher.mymovielist.utils.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        registerText.setOnClickListener {
            sendToRegisterActivity()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val isEmailValid = validateEmail(email)
            val isPasswordNotEmpty = checkIsPasswordNotEmpty(password)
            if (isEmailValid && isPasswordNotEmpty) {
                loginUser(email, password)
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

    private fun sendToRegisterActivity(){
        val intent  = Intent(this@LoginActivity,RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun loginUser(email:String,password: String){
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->

                if(task.isSuccessful){
                    val intent = Intent(this@LoginActivity,HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                }else{
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }
    }
}
