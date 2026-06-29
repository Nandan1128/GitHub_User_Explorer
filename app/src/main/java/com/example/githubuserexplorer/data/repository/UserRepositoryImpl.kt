package com.example.githubuserexplorer.data.repository

import com.example.githubuserexplorer.data.local.UserDao
import com.example.githubuserexplorer.data.mapper.toDomain
import com.example.githubuserexplorer.data.mapper.toEntity
import com.example.githubuserexplorer.data.remote.GitHubApiService
import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api : GitHubApiService,
    private val dao: UserDao
) : GitHubRepository {

    override suspend fun getUser(username: String): User? {
        return try {
            // 1. Call api - return GithubUserDto
            val dto = api.getUser(username)
            // 2. Convert api model to domain model
            val user = dto.toDomain()
            // 3. Cache in Room (convert domain to entity first)
            dao.insertUser(user.toEntity())
            // 4. Return domain model to UseCase
            user
        }catch (e : Exception){
            null
        }
    }

    override fun getRecentSearches(): Flow<List<User>> {
        return dao.getAllUsers().map { entities ->
            //Convert each UserEntity to User (domain model)
            entities.map { it.toDomain()}
        }
    }
}