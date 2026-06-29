package com.example.githubuserexplorer.domain.Model.Repository

import com.example.githubuserexplorer.domain.Model.User
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {

    // Returns null if user not found or network error
    suspend fun getUser(username : String) : User?

    // Flow — emits a new list every time the database changes
    fun getRecentSearches(): Flow<List<User>>
}