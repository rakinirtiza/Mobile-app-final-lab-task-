package com.university.universitynewsapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.university.universitynewsapp.adapter.PostAdapter
import com.university.universitynewsapp.model.Post
import com.university.universitynewsapp.repository.PostRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var repository: PostRepository

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var btnUsers: Button
    private lateinit var etSearch: EditText

    private var postList = listOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        progressBar = findViewById(R.id.progressBar)
        btnUsers = findViewById(R.id.btnUsers)
        etSearch = findViewById(R.id.etSearch)

        repository = PostRepository()

        adapter = PostAdapter(postList)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        loadPosts()

        // USERS BUTTON

        btnUsers.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    UsersActivity::class.java
                )
            )
        }

        // SEARCH

        etSearch.addTextChangedListener(

            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    filterPosts(s.toString())
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            })

        // REFRESH

        swipeRefresh.setOnRefreshListener {

            loadPosts()
        }
    }

    private fun loadPosts() {

        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {

            try {

                val posts =
                    repository.getPosts()

                postList = posts

                adapter.updateData(posts)

            } catch (e: Exception) {

                Toast.makeText(
                    this@MainActivity,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            progressBar.visibility = View.GONE

            swipeRefresh.isRefreshing = false
        }
    }

    private fun filterPosts(text: String) {

        val filteredList =
            postList.filter {

                it.title.contains(
                    text,
                    ignoreCase = true
                )
            }

        adapter.updateData(filteredList)
    }
}