package com.university.universitynewsapp.adapter

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.university.universitynewsapp.R

import com.university.universitynewsapp.model.Comment

class CommentAdapter(

    private val list: List<Comment>

) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(view: View)

        : RecyclerView.ViewHolder(view) {

        val name: TextView =
            view.findViewById(R.id.tvCommentName)

        val email: TextView =
            view.findViewById(R.id.tvCommentEmail)

        val body: TextView =
            view.findViewById(R.id.tvCommentBody)
    }

    override fun onCreateViewHolder(

        parent: ViewGroup,

        viewType: Int

    ): CommentViewHolder {

        val view = LayoutInflater
            .from(parent.context)

            .inflate(
                R.layout.item_comment,
                parent,
                false
            )

        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(

        holder: CommentViewHolder,

        position: Int
    ) {

        val comment = list[position]

        holder.name.text = comment.name

        holder.email.text = comment.email

        holder.body.text = comment.body
    }
}