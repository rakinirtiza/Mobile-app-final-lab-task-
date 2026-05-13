package com.university.studentauthapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var imgAvatar: ImageView

    private lateinit var tvWelcome: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUid: TextView
    private lateinit var tvStatus: TextView

    private lateinit var etNewPassword: EditText

    private lateinit var btnChangePassword: Button
    private lateinit var btnDeleteAccount: Button
    private lateinit var btnLogout: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        imgAvatar = findViewById(R.id.imgAvatar)

        tvWelcome = findViewById(R.id.tvWelcome)
        tvEmail = findViewById(R.id.tvEmail)
        tvUid = findViewById(R.id.tvUid)
        tvStatus = findViewById(R.id.tvStatus)

        etNewPassword =
            findViewById(R.id.etNewPassword)

        btnChangePassword =
            findViewById(R.id.btnChangePassword)

        btnDeleteAccount =
            findViewById(R.id.btnDeleteAccount)

        btnLogout =
            findViewById(R.id.btnLogout)

        val user = auth.currentUser

        if (user != null) {

            val email = user.email.toString()

            tvWelcome.text = "Welcome Student"

            tvEmail.text = "Email: $email"

            tvUid.text =
                "UID: ${user.uid.take(8)}"

            tvStatus.text =
                "You are logged in"
        }

        btnLogout.setOnClickListener {

            auth.signOut()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }

        btnChangePassword.setOnClickListener {

            val newPassword =
                etNewPassword.text.toString().trim()

            if (newPassword.isEmpty()) {

                etNewPassword.error =
                    "Enter New Password"

                return@setOnClickListener
            }

            user?.updatePassword(newPassword)
                ?.addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Password Updated",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            this,
                            task.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        btnDeleteAccount.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Delete Account")

                .setMessage(
                    "Are you sure you want to delete account?"
                )

                .setPositiveButton("Yes") { _, _ ->

                    user?.delete()
                        ?.addOnCompleteListener { task ->

                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this,
                                    "Account Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(
                                        this,
                                        LoginActivity::class.java
                                    )
                                )

                                finish()

                            } else {

                                Toast.makeText(
                                    this,
                                    task.exception?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }

                .setNegativeButton("No", null)

                .show()
        }
    }
}