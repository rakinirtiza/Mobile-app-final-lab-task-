package com.university.universitynewsapp.network

import com.university.universitynewsapp.model.Comment

import com.university.universitynewsapp.model.Post

import com.university.universitynewsapp.model.User

import retrofit2.http.GET

import retrofit2.http.Path

interface ApiService {

    @GET("posts")

    suspend fun getAllPosts():
            List<Post>

    @GET("posts/{id}/comments")

    suspend fun getCommentsByPost(

        @Path("id") postId: Int

    ): List<Comment>

    @GET("users")

    suspend fun getAllUsers():
            List<User>

    @GET("users/{id}")

    suspend fun getUserById(

        @Path("id") id: Int

    ): User
}