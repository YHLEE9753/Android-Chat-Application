package com.example.chatproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project.User
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {


    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        // hide actionBar
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        // Link with each view
        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener{
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            FirebaseApp.initializeApp(this);
            signUp(name,email,password)
        }
    }

    private fun signUp(name: String, email: String, password: String){
        //logic of creating user
        //https://firebase.google.com/docs/auth/android/password-auth
        //get information from firebase docs
        Log.d("ITM",email)
        Log.d("ITM",password)
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("ITM","success signup")
                    // code for jumping to home

                    // add user To Database
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!) // none safe!

                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Log.d("ITM","fail signup")
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // add user To Database
    private fun addUserToDatabase(name:String, email:String, uid:String){
        // use real
        Log.d("ITM","function start")
        mDbRef = Firebase.database.reference
        Log.d("ITM",uid)
        Log.d("ITM",name)
        Log.d("ITM",email)
        mDbRef.child("user").child(uid).setValue(User(name, email, uid)) // add user to database

    }
}