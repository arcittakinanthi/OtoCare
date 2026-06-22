package com.arcittakinanthi.otocare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arcittakinanthi.otocare.screen.DetailScreen
import com.arcittakinanthi.otocare.screen.LoginScreen
import com.arcittakinanthi.otocare.screen.MainScreen
import com.arcittakinanthi.otocare.screen.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Home.route) {
            MainScreen(
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(Screen.FormBaru.route)
                },
                onEditClick = { id ->
                    navController.navigate(Screen.FormUbah.withId(id))
                }
            )
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L

            DetailScreen(
                id = id,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Login.route) {

            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }
    }
}