package com.university.usersettings

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerDepartment: Spinner
    private lateinit var spinnerYear: Spinner
    private lateinit var tvWelcome: TextView
    private lateinit var btnSaveProfile: Button

    private val departments = arrayOf("CSE", "EEE", "BBA", "English", "Law")
    private val years = arrayOf("1st Year", "2nd Year", "3rd Year", "4th Year")

    companion object {
        const val PREF_PROFILE = "ProfilePrefs"

        const val KEY_STUDENT_ID = "KEY_STUDENT_ID"
        const val KEY_STUDENT_NAME = "KEY_STUDENT_NAME"
        const val KEY_DEPARTMENT = "KEY_DEPARTMENT"
        const val KEY_YEAR = "KEY_YEAR"
        const val KEY_EMAIL = "KEY_EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Setup"

        bindViews()
        setupSpinners()
        restoreProfile()

        btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun bindViews() {
        etStudentId = findViewById(R.id.etStudentId)
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        spinnerDepartment = findViewById(R.id.spinnerDepartment)
        spinnerYear = findViewById(R.id.spinnerYear)
        tvWelcome = findViewById(R.id.tvWelcome)
        btnSaveProfile = findViewById(R.id.btnSaveProfile)
    }

    private fun setupSpinners() {
        val departmentAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            departments
        )
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDepartment.adapter = departmentAdapter

        val yearAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            years
        )
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter
    }

    private fun saveProfile() {
        val studentId = etStudentId.text.toString().trim()
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val department = spinnerDepartment.selectedItem.toString()
        val year = spinnerYear.selectedItem.toString()

        val prefs = getSharedPreferences(PREF_PROFILE, Context.MODE_PRIVATE)

        prefs.edit()
            .putString(KEY_STUDENT_ID, studentId)
            .putString(KEY_STUDENT_NAME, fullName)
            .putString(KEY_DEPARTMENT, department)
            .putString(KEY_YEAR, year)
            .putString(KEY_EMAIL, email)
            .apply()

        tvWelcome.text = "Welcome back, $fullName!"

        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show()
    }

    private fun restoreProfile() {
        val prefs = getSharedPreferences(PREF_PROFILE, Context.MODE_PRIVATE)

        val studentId = prefs.getString(KEY_STUDENT_ID, "")
        val fullName = prefs.getString(KEY_STUDENT_NAME, "")
        val department = prefs.getString(KEY_DEPARTMENT, "CSE")
        val year = prefs.getString(KEY_YEAR, "1st Year")
        val email = prefs.getString(KEY_EMAIL, "")

        etStudentId.setText(studentId)
        etFullName.setText(fullName)
        etEmail.setText(email)

        val departmentIndex = departments.indexOf(department)
        if (departmentIndex >= 0) {
            spinnerDepartment.setSelection(departmentIndex)
        }

        val yearIndex = years.indexOf(year)
        if (yearIndex >= 0) {
            spinnerYear.setSelection(yearIndex)
        }

        if (!fullName.isNullOrEmpty()) {
            tvWelcome.text = "Welcome back, $fullName!"
        } else {
            tvWelcome.text = "Welcome"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}