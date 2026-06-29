package com.example.githubuserexplorer.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubuserexplorer.presentation.Screen.screen.ProfileScreen
import com.example.githubuserexplorer.presentation.Screen.screen.SearchScreen

object Routes {
    const val SEARCH = "search"
    const val PROFILE = "profile/{username}"
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.SEARCH) {
        composable(Routes.SEARCH) {
            SearchScreen(navController)
        }
        composable(Routes.PROFILE,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ProfileScreen(username, navController)
        }
    }
}
