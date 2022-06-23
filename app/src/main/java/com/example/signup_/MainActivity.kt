package com.example.signup_
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {


    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null


    lateinit var saveButton: Button
    lateinit var clearButton: Button
    lateinit var email: EditText
    lateinit var firstname: EditText
    lateinit var lastname: EditText
    lateinit var username: EditText
    lateinit var age: EditText
    lateinit var password: EditText
    val MIN_username_LENGTH = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        email = findViewById(R.id.email)
        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        username = findViewById(R.id.username)
        age = findViewById(R.id.age)
        password = findViewById(R.id.password)
        signup()
    }

    private fun signup() {

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {

            var email = email.text.toString()
            var username = username.text.toString()
            var password = password.text.toString()
            var firstname = firstname.text.toString()
            var lastname = lastname.text.toString()
            var age = age.text.toString()


            if (email.isEmpty() && password.isEmpty() && username.isEmpty() && firstname.isEmpty() && lastname.isEmpty() && age.isEmpty()) {
                Toast.makeText(this, "PLEASE, ENTER YOUR DATA", Toast.LENGTH_LONG).show()
            }

            if (email.contains("@")) {
                Toast.makeText(this, "EMAIL IS VALID!", Toast.LENGTH_LONG).show()

                if (email.isEmpty() || password.isEmpty() || username.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || age.isEmpty())
                    Toast.makeText(this, "ENTER YOUR DATA UN ALL FIELDS! !", Toast.LENGTH_LONG).show()

                if (username.length < MIN_username_LENGTH) {
                    Toast.makeText(this, "USERNAME MUST BE AT LEAST 10 CHARACTER", Toast.LENGTH_LONG).show()
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("! ! !")
                                builder.setMessage("YOU ARE SIGNED IN! YOUR PERSONAL DATA WILL BE PROTECTED IN THE DATABASE!")
                                builder.setPositiveButton("clear") { dialog, i ->
                                    dialog.dismiss()
                                    finish()
                                }
                                builder.show().setCanceledOnTouchOutside(false)
                            } else {
                                Toast.makeText(this, "CHECK ALL FIELDS!", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                }

            }

            clearButton = findViewById(R.id.clearButton)
            clearButton.setOnLongClickListener {

                var text = "clear values"
                true

            }

        }
    }

}
