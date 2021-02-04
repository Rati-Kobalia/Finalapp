package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var profilePictureURLEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var nextButton: Button
    private lateinit var imageView: ImageView
    lateinit var userName: TextView
    lateinit var signOutButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        nameEditText = findViewById(R.id.nameEditText)
        profilePictureURLEditText = findViewById(R.id.profilePictureURLEditText)
        saveButton = findViewById(R.id.saveButton)
        nextButton = findViewById(R.id.nextButton)
        imageView = findViewById(R.id.imageView)
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("personInfo")
        userName = findViewById(R.id.userName)
        signOutButton = findViewById(R.id.signOutButton)

        userName.text = "Registered Email Address:  " + mAuth.currentUser?.email

        signOutButton.setOnClickListener{
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        nextButton.setOnClickListener{
            startActivity(Intent(this, PageActivity::class.java))
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val url = profilePictureURLEditText.text.toString()

            val personInfo = persoInfo(name, url)

            if (mAuth.currentUser?.uid != null) {
                db.child(mAuth.currentUser?.uid!!).setValue(personInfo).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "successful!", Toast.LENGTH_SHORT).show()
                        nameEditText.text = null
                        profilePictureURLEditText.text = null
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            }



        }

        if (mAuth.currentUser?.uid != null) {
            db.child(mAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@UserActivity, "Error!", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    val p = snapshot.getValue(persoInfo::class.java)

                    if (p != null) {

                        Glide.with(this@UserActivity)
                            .load(p.url)
                            .centerCrop()
                            .into(imageView)

                    }
                }

            })
        }


    }
}