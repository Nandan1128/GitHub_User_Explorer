package com.example.githubuserexplorer.presentation.Screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserexplorer.domain.Model.User
import com.example.githubuserexplorer.domain.Model.Repository.GitHubRepository
import com.example.githubuserexplorer.domain.Model.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class ProfileVIewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun loadProfile(username : String){
        viewModelScope.launch {
            _uiState.value = ProfileUiState(isLoading = true)
            try {
                val user = withContext(ioDispatcher){
                    getUserUseCase(username) // operator invoke - called like a function
                }
                _uiState.value = if (user != null){
                    ProfileUiState(user = user)
                }else {
                    ProfileUiState(error = "User Not Found")
                }
            }catch (e : Exception){
                _uiState.value = ProfileUiState(error = e.message ?: "Something went wrong")
            }
        }
    }
}

data class ProfileUiState(
    val isLoading : Boolean = false,
    val user: User? = null,
    val error : String? = null
)
