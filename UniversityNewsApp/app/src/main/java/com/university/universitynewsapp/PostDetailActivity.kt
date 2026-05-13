package com.university.universitynewsapp

import android.os.Bundle

import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import com.university.universitynewsapp.adapter.CommentAdapter

import com.university.universitynewsapp.network.RetrofitClient

import kotlinx.coroutines.launch

class PostDetailActivity : AppCompatActivity() {

    private lateinit var titleText: TextView

    private lateinit var bodyText: TextView

    private lateinit var idText: TextView

    private lateinit var commentRecycler:
            RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_post_detail
        )

        title = "Post Detail"

        titleText =
            findViewById(R.id.tvDetailTitle)

        bodyText =
            findViewById(R.id.tvDetailBody)

        idText =
            findViewById(R.id.tvDetailId)

        commentRecycler =
            findViewById(R.id.commentRecyclerView)

        commentRecycler.layoutManager =
            LinearLayoutManager(this)

        val postId =
            intent.getIntExtra(
                "postId",
                0
            )

        loadPost(postId)

        loadComments(postId)
    }

    private fun loadPost(id: Int) {

        lifecycleScope.launch {

            try {

                val post =
                    RetrofitClient.api
                        .getAllPosts()[id - 1]

                titleText.text =
                    post.title

                bodyText.text =
                    post.body

                idText.text =
                    "Post ID: ${post.id}"

            } catch (e: Exception) {

                bodyText.text =
                    e.message
            }
        }
    }

    private fun loadComments(postId: Int) {

        lifecycleScope.launch {

            try {

                val comments =
                    RetrofitClient.api
                        .getCommentsByPost(postId)

                commentRecycler.adapter =
                    CommentAdapter(comments)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}