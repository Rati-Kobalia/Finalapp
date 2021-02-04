package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


private lateinit var inputEmail: EditText
private lateinit var inputPassword: EditText
private lateinit var SignUpButton: Button
private lateinit var GoToLogin: Button
private lateinit var mAuth: FirebaseAuth


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        inputEmail = findViewById(R.id.inputEmail2)
        inputPassword = findViewById(R.id.inputPassword2)
        SignUpButton = findViewById(R.id.SignUpButton)
        GoToLogin = findViewById(R.id.gotologin)
        mAuth = FirebaseAuth.getInstance();

        GoToLogin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        SignUpButton.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, UserActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}