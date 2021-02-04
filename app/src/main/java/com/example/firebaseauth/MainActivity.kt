package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var LogInButton: Button
    private lateinit var GoToSignup: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        LogInButton = findViewById(R.id.LogInButton)
        GoToSignup = findViewById(R.id.gotosignup)
        mAuth = FirebaseAuth.getInstance();

        GoToSignup.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }


        LogInButton.setOnClickListener{
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()


            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill out required fields", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
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