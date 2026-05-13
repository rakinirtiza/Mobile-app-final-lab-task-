package com.university.studentcoursemanager

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditCourseActivity : AppCompatActivity() {

    private lateinit var etCourseName: EditText
    private lateinit var etCourseCode: EditText
    private lateinit var etInstructor: EditText
    private lateinit var etCreditHours: EditText
    private lateinit var etSchedule: EditText
    private lateinit var etRoom: EditText
    private lateinit var etSemester: EditText

    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private var courseId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_course)

        etCourseName = findViewById(R.id.etCourseName)
        etCourseCode = findViewById(R.id.etCourseCode)
        etInstructor = findViewById(R.id.etInstructor)
        etCreditHours = findViewById(R.id.etCreditHours)
        etSchedule = findViewById(R.id.etSchedule)
        etRoom = findViewById(R.id.etRoom)
        etSemester = findViewById(R.id.etSemester)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        courseId = intent.getStringExtra("courseId") ?: ""

        etCourseName.setText(
            intent.getStringExtra("courseName")
        )

        etCourseCode.setText(
            intent.getStringExtra("courseCode")
        )

        etInstructor.setText(
            intent.getStringExtra("instructor")
        )

        etCreditHours.setText(
            intent.getStringExtra("creditHours")
        )

        etSchedule.setText(
            intent.getStringExtra("schedule")
        )

        etRoom.setText(
            intent.getStringExtra("room")
        )

        etSemester.setText(
            intent.getStringExtra("semester")
        )

        btnUpdate.setOnClickListener {

            val updatedCourse = Course(
                id = courseId,
                courseName = etCourseName.text.toString(),
                courseCode = etCourseCode.text.toString(),
                instructor = etInstructor.text.toString(),
                creditHours = etCreditHours.text.toString(),
                schedule = etSchedule.text.toString(),
                room = etRoom.text.toString(),
                semester = etSemester.text.toString()
            )

            FirebaseDatabase
                .getInstance()
                .getReference("Courses")
                .child(courseId)
                .setValue(updatedCourse)

            Toast.makeText(
                this,
                "Course Updated",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }

        btnDelete.setOnClickListener {

            FirebaseDatabase
                .getInstance()
                .getReference("Courses")
                .child(courseId)
                .removeValue()

            Toast.makeText(
                this,
                "Course Deleted",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}