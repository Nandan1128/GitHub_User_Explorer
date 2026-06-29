package com.example.githubuserexplorer.presentation.Screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserexplorer.data.local.entity.UserEntity
import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.User
import com.example.githubuserexplorer.domain.Model.usecase.GetRecentSearchesUseCase
import com.example.githubuserexplorer.domain.Model.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class SearchViewModel@Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase)
    : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun searchUser(username: String){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val user = getUserUseCase(username)
            _uiState.value = if (user != null){
                _uiState.value.copy(success = true, isLoading = false)
            }else{
                _uiState.value.copy(error = "User not Found", isLoading = false)
            }
        }
    }

    fun loadRecentSearches(){
        viewModelScope.launch {
            getRecentSearchesUseCase().collect { users ->
                _uiState.value = _uiState.value.copy(recentUsers = users)
            }
        }
    }
}

data class SearchUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val recentUsers: List<User> = emptyList()
)