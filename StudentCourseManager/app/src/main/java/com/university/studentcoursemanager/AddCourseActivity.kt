package com.university.studentcoursemanager

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddCourseActivity : AppCompatActivity() {

    private lateinit var etCourseName: EditText
    private lateinit var etCourseCode: EditText
    private lateinit var etInstructor: EditText
    private lateinit var etSchedule: EditText
    private lateinit var etRoom: EditText

    private lateinit var spCredit: Spinner
    private lateinit var spSemester: Spinner

    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        etCourseName = findViewById(R.id.etCourseName)
        etCourseCode = findViewById(R.id.etCourseCode)
        etInstructor = findViewById(R.id.etInstructor)
        etSchedule = findViewById(R.id.etSchedule)
        etRoom = findViewById(R.id.etRoom)

        spCredit = findViewById(R.id.spCredit)
        spSemester = findViewById(R.id.spSemester)

        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        progressBar = findViewById(R.id.progressBar)

        val creditList = arrayOf("1", "2", "3", "4")
        val semesterList = arrayOf(
            "Spring 2025",
            "Summer 2025",
            "Fall 2025"
        )

        spCredit.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            creditList
        )

        spSemester.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            semesterList
        )

        btnSave.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            val id = FirebaseDatabase.getInstance()
                .reference
                .child("courses")
                .push()
                .key!!

            val course = Course(
                id,
                etCourseName.text.toString(),
                etCourseCode.text.toString(),
                etInstructor.text.toString(),
                spCredit.selectedItem.toString(),
                etSchedule.text.toString(),
                etRoom.text.toString(),
                spSemester.selectedItem.toString()
            )

            FirebaseDatabase.getInstance()
                .reference
                .child("courses")
                .child(id)
                .setValue(course)
                .addOnSuccessListener {

                    progressBar.visibility = View.GONE

                    Toast.makeText(
                        this,
                        "Course Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}