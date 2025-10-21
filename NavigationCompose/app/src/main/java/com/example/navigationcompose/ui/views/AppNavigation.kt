package com.example.navigationcompose.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationcompose.viewmodel.ProductViewModel

@Composable
fun AppNavigation(viewModel: ProductViewModel = viewModel()) {
    val navController: NavHostController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                products = uiState.products,
                onNavigateToDatail = { id -> navController.navigate("detail/$id") })
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val product = viewModel.getProductById(id)
            DetailScreen(product = product, onBack = { navController.popBackStack() })

        }
    }

}