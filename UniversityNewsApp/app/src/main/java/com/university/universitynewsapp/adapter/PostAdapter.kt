package com.university.universitynewsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.university.universitynewsapp.PostDetailActivity
import com.university.universitynewsapp.R
import com.university.universitynewsapp.model.Post

class PostAdapter(
    private var list: List<Post>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val title: TextView =
            itemView.findViewById(R.id.titleText)

        val body: TextView =
            itemView.findViewById(R.id.bodyText)

        val id: TextView =
            itemView.findViewById(R.id.idText)

        val card: CardView =
            itemView.findViewById(R.id.cardPost)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {

        val view = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.item_post,
            parent,
            false
        )

        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {

        val post = list[position]

        holder.title.text = post.title

        holder.body.text = post.body

        holder.id.text = "Post ID: ${post.id}"

        holder.card.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                PostDetailActivity::class.java
            )

            intent.putExtra(
                "postId",
                post.id
            )

            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateData(newList: List<Post>) {

        list = newList

        notifyDataSetChanged()
    }
}