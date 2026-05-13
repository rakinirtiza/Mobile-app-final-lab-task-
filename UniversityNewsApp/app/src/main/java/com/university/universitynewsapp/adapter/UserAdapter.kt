package com.university.universitynewsapp.adapter

import android.content.Intent

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.university.universitynewsapp.R

import com.university.universitynewsapp.UserProfileActivity

import com.university.universitynewsapp.model.User

class UserAdapter(

    private val list: List<User>

) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View)

        : RecyclerView.ViewHolder(view) {

        val name: TextView =
            view.findViewById(R.id.tvUserName)

        val username: TextView =
            view.findViewById(R.id.tvUsername)

        val email: TextView =
            view.findViewById(R.id.tvEmail)
    }

    override fun onCreateViewHolder(

        parent: ViewGroup,

        viewType: Int

    ): UserViewHolder {

        val view = LayoutInflater
            .from(parent.context)

            .inflate(
                R.layout.item_user,
                parent,
                false
            )

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(

        holder: UserViewHolder,

        position: Int
    ) {

        val user = list[position]

        holder.name.text = user.name

        holder.username.text =
            "@${user.username}"

        holder.email.text = user.email

        holder.itemView.setOnClickListener {

            val intent = Intent(

                holder.itemView.context,

                UserProfileActivity::class.java
            )

            intent.putExtra(
                "userId",
                user.id
            )

            holder.itemView.context
                .startActivity(intent)
        }
    }
}