package com.example.delvis.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delvis.ui.theme.screens.SplashScreen
import com.example.delvis.ui.theme.screens.dashboard.DashboardScreen
import com.example.delvis.ui.theme.screens.jobs.AddjobScreen
import com.example.delvis.ui.theme.screens.jobs.UpdatejobScreen
import com.example.delvis.ui.theme.screens.jobs.ViewJobs
import com.example.delvis.ui.theme.screens.login.LoginScreen
import com.example.delvis.ui.theme.screens.applications.AddproductScreen
import com.example.delvis.ui.theme.screens.applications.UpdateproductScreen
import com.example.delvis.ui.theme.screens.productsimport.ViewProducts
import com.example.delvis.ui.theme.screens.register.RegisterScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), startDestination: String = ROUTE_SPLASH) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_SPLASH) {
            SplashScreen {
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }
        }
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
        composable(ROUTE_LOGIN) { LoginScreen(navController) }
        composable(ROUTE_DASHBOARD) { DashboardScreen(navController) }
        composable(ROUTE_ADD_PRODUCT) { AddproductScreen(navController) }
        composable(ROUTE_VIEW_PRODUCTS) { ViewProducts(navController) }

        composable("$ROUTE_UPDATE_PRODUCT/{productId}") { backStackEntry ->
            backStackEntry.arguments?.getString("productId")?.let {
                UpdateproductScreen(navController, it)
            }
        }

        composable(ROUTE_ADD_JOB) { AddjobScreen(navController) }
        composable(ROUTE_VIEW_JOBS) { ViewJobs(navController) }

        composable("$ROUTE_UPDATE_JOB/{jobId}") { backStackEntry ->
            backStackEntry.arguments?.getString("jobId")?.let {
                UpdatejobScreen(navController, it)
            }
        }
    }
}
