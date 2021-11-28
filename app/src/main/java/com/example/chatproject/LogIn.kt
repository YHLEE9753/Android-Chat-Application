package com.example.chatproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        // hide actionBar
        supportActionBar?.hide()

        // get instance from firebase
        mAuth = FirebaseAuth.getInstance()

        // Link with each view
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        // intent to signup
        btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            finish()
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            login(email,password)
        }

    }

    private fun login(email: String, password: String){
        //logic for logging user
        //https://firebase.google.com/docs/auth/android/password-auth
        //get information from firebase docs
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user

                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}