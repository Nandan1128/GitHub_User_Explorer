package com.example.githubuserexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val login : String,
    val name : String?,
    val avatarUrl: String
)