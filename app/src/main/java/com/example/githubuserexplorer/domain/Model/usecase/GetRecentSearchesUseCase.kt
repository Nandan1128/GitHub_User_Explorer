package com.example.githubuserexplorer.domain.Model.usecase

import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchesUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    operator fun invoke(): Flow<List<User>>{
        return repository.getRecentSearches()
    }
}