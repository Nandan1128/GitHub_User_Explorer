package com.example.githubuserexplorer.data.remote

import com.example.githubuserexplorer.data.remote.dto.GitHubUserDto
import com.example.githubuserexplorer.domain.Model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): GitHubUserDto
}