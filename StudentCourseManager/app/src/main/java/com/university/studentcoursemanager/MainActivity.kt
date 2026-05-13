package com.university.studentcoursemanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewCourses: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var fabAddCourse: FloatingActionButton
    private lateinit var searchView: SearchView

    private lateinit var databaseReference: DatabaseReference

    private lateinit var adapter: CourseAdapter

    private var courseList = ArrayList<Course>()
    private var originalList = ArrayList<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerViewCourses =
            findViewById(R.id.recyclerViewCourses)

        tvEmpty =
            findViewById(R.id.tvEmpty)

        fabAddCourse =
            findViewById(R.id.fabAddCourse)

        searchView =
            findViewById(R.id.searchView)

        recyclerViewCourses.layoutManager =
            LinearLayoutManager(this)

        adapter =
            CourseAdapter(this, courseList)

        recyclerViewCourses.adapter =
            adapter

        databaseReference =
            FirebaseDatabase.getInstance()
                .getReference("courses")

        loadCourses()

        fabAddCourse.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddCourseActivity::class.java
                )
            )
        }

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(
                    query: String?
                ): Boolean {
                    return false
                }

                override fun onQueryTextChange(
                    newText: String?
                ): Boolean {

                    val filteredList =
                        ArrayList<Course>()

                    for (course in originalList) {

                        if (
                            course.courseName.contains(
                                newText ?: "",
                                true
                            )
                            ||
                            course.courseCode.contains(
                                newText ?: "",
                                true
                            )
                        ) {

                            filteredList.add(course)
                        }
                    }

                    adapter.updateList(filteredList)

                    return true
                }
            }
        )
    }

    private fun loadCourses() {

        databaseReference.addValueEventListener(

            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    courseList.clear()
                    originalList.clear()

                    for (data in snapshot.children) {

                        val course =
                            data.getValue(Course::class.java)

                        if (course != null) {

                            courseList.add(course)

                            originalList.add(course)
                        }
                    }

                    adapter.notifyDataSetChanged()

                    if (courseList.isEmpty()) {

                        tvEmpty.visibility =
                            View.VISIBLE

                    } else {

                        tvEmpty.visibility =
                            View.GONE
                    }
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {

                }
            }
        )
    }
}