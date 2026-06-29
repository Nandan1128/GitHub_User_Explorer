package com.example.githubuserexplorer.presentation.Screen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.githubuserexplorer.presentation.Screen.viewModel.ProfileVIewModel

@Composable
fun ProfileScreen(username: String, navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: ProfileVIewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(username) {

        viewModel.loadProfile(username)
    }
    when{
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(uiState.error ?: "Unknown Error")
            }
        }
        uiState.user != null -> {
            val user = uiState.user!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(user.name ?: "No Name", style = MaterialTheme.typography.bodyMedium)
                Text("@${user.login}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Followers : ${user.followers}")
                Text("Following : ${user.following}")
                Text("Public Repos : ${user.publicRepos}")
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Back")
                }
            }
        }

    }
}