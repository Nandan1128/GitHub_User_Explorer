package com.example.githubuserexplorer.data.mapper

import com.example.githubuserexplorer.data.local.entity.UserEntity
import com.example.githubuserexplorer.data.remote.dto.GitHubUserDto
import com.example.githubuserexplorer.domain.Model.User


fun GitHubUserDto.toDomain(): User = User(
    login = login,
    name = name,
    followers = followers,
    following = following,
    publicRepos = pubilcRepos,
    avatarUrl = avatarUrl,
)

fun User.toEntity(): UserEntity = UserEntity(
    login = login,
    name = name,
    avatarUrl = avatarUrl
)

fun UserEntity.toDomain(): User = User(
    login = login,
    name = name,
    followers = 0,
    following = 0,
    publicRepos = 0,
    avatarUrl = avatarUrl
)