package com.university.studentcoursemanager

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CourseDetailActivity : AppCompatActivity() {

    private lateinit var txtCourseName: TextView
    private lateinit var txtCourseCode: TextView
    private lateinit var txtInstructor: TextView
    private lateinit var txtCredit: TextView
    private lateinit var txtSchedule: TextView
    private lateinit var txtRoom: TextView
    private lateinit var txtSemester: TextView

    private lateinit var btnEdit: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        txtCourseName = findViewById(R.id.txtCourseName)
        txtCourseCode = findViewById(R.id.txtCourseCode)
        txtInstructor = findViewById(R.id.txtInstructor)
        txtCredit = findViewById(R.id.txtCredit)
        txtSchedule = findViewById(R.id.txtSchedule)
        txtRoom = findViewById(R.id.txtRoom)
        txtSemester = findViewById(R.id.txtSemester)

        btnEdit = findViewById(R.id.btnEdit)

        val courseId = intent.getStringExtra("courseId") ?: ""
        val courseName = intent.getStringExtra("courseName") ?: ""
        val courseCode = intent.getStringExtra("courseCode") ?: ""
        val instructor = intent.getStringExtra("instructor") ?: ""
        val creditHours = intent.getStringExtra("creditHours") ?: ""
        val schedule = intent.getStringExtra("schedule") ?: ""
        val room = intent.getStringExtra("room") ?: ""
        val semester = intent.getStringExtra("semester") ?: ""

        txtCourseName.text = courseName
        txtCourseCode.text = "Course Code: $courseCode"
        txtInstructor.text = "Instructor: $instructor"
        txtCredit.text = "Credits: $creditHours"
        txtSchedule.text = "Schedule: $schedule"
        txtRoom.text = "Room: $room"
        txtSemester.text = "Semester: $semester"

        btnEdit.setOnClickListener {

            val intent = Intent(
                this,
                EditCourseActivity::class.java
            )

            intent.putExtra("courseId", courseId)
            intent.putExtra("courseName", courseName)
            intent.putExtra("courseCode", courseCode)
            intent.putExtra("instructor", instructor)
            intent.putExtra("creditHours", creditHours)
            intent.putExtra("schedule", schedule)
            intent.putExtra("room", room)
            intent.putExtra("semester", semester)

            startActivity(intent)
        }
    }
}