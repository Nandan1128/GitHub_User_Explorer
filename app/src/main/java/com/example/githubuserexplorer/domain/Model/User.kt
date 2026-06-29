package com.example.githubuserexplorer.domain.Model

import com.google.gson.annotations.SerializedName

data class User(
    val login : String,
    val name : String?,
    val followers : Int,
    val following : Int,
    @SerializedName("public_repos")
    val publicRepos : Int,
    @SerializedName("avatar_url")
    val avatarUrl : String
)
