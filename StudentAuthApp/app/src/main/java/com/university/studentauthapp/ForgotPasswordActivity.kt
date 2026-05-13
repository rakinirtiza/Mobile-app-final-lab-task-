package com.university.studentauthapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnReset: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        btnReset = findViewById(R.id.btnReset)

        btnReset.setOnClickListener {

            val email = etEmail.text.toString().trim()

            if (email.isEmpty()) {

                etEmail.error = "Enter Email"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Reset email sent",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()

                    } else {

                        Snackbar.make(
                            findViewById(android.R.id.content),
                            task.exception?.message.toString(),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}