package com.example.githubuserexplorer.domain.Model.usecase

import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    suspend operator  fun invoke(username : String) : User? {
        if (username.isBlank()) return null
        return repository.getUser(username.trim().lowercase())
    }
}