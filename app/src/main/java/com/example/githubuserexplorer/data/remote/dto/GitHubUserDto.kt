package com.example.githubuserexplorer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GitHubUserDto(
    val login : String,
    val name : String?,
    val followers : Int,
    val following : Int,
    @SerializedName("public_repos")
    val pubilcRepos: Int,
    @SerializedName("avatar_url")
    val avatarUrl : String
)
