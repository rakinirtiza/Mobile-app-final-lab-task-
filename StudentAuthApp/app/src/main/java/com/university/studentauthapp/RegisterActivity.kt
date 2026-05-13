package com.university.studentauthapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLogin: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvLogin = findViewById(R.id.tvLogin)
        progressBar = findViewById(R.id.progressBar)

        btnRegister.setOnClickListener {
            registerUser()
        }

        tvLogin.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }
    }

    private fun registerUser() {

        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword =
            etConfirmPassword.text.toString().trim()

        if (name.isEmpty()) {

            etName.error = "Enter Name"
            etName.requestFocus()
            return
        }

        if (email.isEmpty()) {

            etEmail.error = "Enter Email"
            etEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            etEmail.error = "Enter Valid Email"
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {

            etPassword.error = "Enter Password"
            etPassword.requestFocus()
            return
        }

        if (password.length < 8) {

            etPassword.error =
                "Password must be at least 8 characters"

            etPassword.requestFocus()
            return
        }

        if (confirmPassword != password) {

            etConfirmPassword.error =
                "Password does not match"

            etConfirmPassword.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener(this) { task ->

            progressBar.visibility = View.GONE

            if (task.isSuccessful) {

                Toast.makeText(
                    this,
                    "Registration Successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, HomeActivity::class.java)
                )

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