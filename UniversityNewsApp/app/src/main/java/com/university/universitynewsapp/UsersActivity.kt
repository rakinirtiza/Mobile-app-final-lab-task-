package com.university.universitynewsapp

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import com.university.universitynewsapp.adapter.UserAdapter

import com.university.universitynewsapp.network.RetrofitClient

import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {

    private lateinit var recyclerView:
            RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_users
        )

        title = "Users"

        recyclerView =
            findViewById(R.id.usersRecyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        loadUsers()
    }

    private fun loadUsers() {

        lifecycleScope.launch {

            try {

                val users =
                    RetrofitClient.api
                        .getAllUsers()

                recyclerView.adapter =
                    UserAdapter(users)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}