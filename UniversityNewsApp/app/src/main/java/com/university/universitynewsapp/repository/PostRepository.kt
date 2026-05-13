package com.university.universitynewsapp.repository

import com.university.universitynewsapp.network.RetrofitClient

class PostRepository {

    private val api =
        RetrofitClient.api

    suspend fun getPosts() =
        api.getAllPosts()
}