package com.university.studentcoursemanager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class CourseAdapter(
    private val context: Context,
    private var courseList: ArrayList<Course>
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtCourseName: TextView =
            itemView.findViewById(R.id.txtCourseName)

        val txtCourseCode: TextView =
            itemView.findViewById(R.id.txtCourseCode)

        val txtInstructor: TextView =
            itemView.findViewById(R.id.txtInstructor)

        val txtCreditHours: TextView =
            itemView.findViewById(R.id.txtCreditHours)

        val txtSchedule: TextView =
            itemView.findViewById(R.id.txtSchedule)

        val btnEdit: ImageButton =
            itemView.findViewById(R.id.btnEdit)

        val btnDelete: ImageButton =
            itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_course, parent, false)

        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(
        holder: CourseViewHolder,
        position: Int
    ) {

        val course = courseList[position]

        holder.txtCourseName.text = course.courseName

        holder.txtCourseCode.text = course.courseCode

        holder.txtInstructor.text =
            "Instructor: ${course.instructor}"

        holder.txtCreditHours.text =
            "Credits: ${course.creditHours}"

        holder.txtSchedule.text =
            course.schedule

        holder.btnEdit.setOnClickListener {

            val intent =
                Intent(context, EditCourseActivity::class.java)

            intent.putExtra("course", course)

            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {

            FirebaseDatabase.getInstance()
                .getReference("courses")
                .child(course.id)
                .removeValue()
        }

        holder.itemView.setOnClickListener {

            val intent =
                Intent(context, CourseDetailActivity::class.java)

            intent.putExtra("course", course)

            context.startActivity(intent)
        }
    }

    fun updateList(newList: ArrayList<Course>) {
        courseList = newList
        notifyDataSetChanged()
    }
}