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
import com.example.delvis.ui.theme.screens.applications.AddApplicationScreen
import com.example.delvis.ui.theme.screens.applications.ApplicationBoard
import com.example.delvis.ui.theme.screens.applications.UpdateApplicationScreen
import com.example.delvis.ui.theme.screens.jobs.JobSearchScreen
import com.example.delvis.ui.theme.screens.productsimport.ViewApplications
import com.example.delvis.ui.theme.screens.register.RegisterScreen

//@Composable
//fun AppNavHost(navController: NavHostController = rememberNavController(), startDestination: String = ROUTE_SPLASH) {
//    NavHost(navController = navController, startDestination = startDestination) {
//        composable(ROUTE_SPLASH) {
//            SplashScreen {
//                navController.navigate(ROUTE_REGISTER) {
//                    popUpTo(ROUTE_SPLASH) { inclusive = true }
//                }
//            }
//        }
//        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
//        composable(ROUTE_LOGIN) { LoginScreen(navController) }
//        composable(ROUTE_DASHBOARD) { DashboardScreen(navController) }
//
//        composable(ROUTE_ADD_JOB) { AddjobScreen(navController) }
//        composable(ROUTE_VIEW_JOBS) { ViewJobs(navController) }
//        composable(ROUTE_SEARCH_JOB) { JobSearchScreen(navController) }
//        composable("$ROUTE_UPDATE_JOB/{jobId}") { backStackEntry ->
//            backStackEntry.arguments?.getString("jobId")?.let {
//                UpdatejobScreen(navController, it)
//            }
//        }
//        composable(ROUTE_ADD_APPLICATION) { AddApplicationScreen(navController) }
//        composable(ROUTE_APPLICATION_BOARD) { ApplicationBoard(navController) }
//        composable(ROUTE_VIEW_APPLICATION) { ViewApplications(navController) }
//        composable("$ROUTE_UPDATE_APPLICATION/{jobId}") { backStackEntry ->
//            backStackEntry.arguments?.getString("applicationId")?.let {
//                UpdateApplicationScreen(navController, it)
//            }
//        }
//    }
//}



@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_SPLASH) {
            // Pass the controller in and let SplashScreen perform its own navigation
            SplashScreen(navController)
        }
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }
        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }
        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(ROUTE_ADD_JOB) {
            AddjobScreen(navController)
        }
        composable(ROUTE_VIEW_JOBS) {
            ViewJobs(navController)
        }
        composable(ROUTE_SEARCH_JOB) {
            JobSearchScreen(navController)
        }
        composable("$ROUTE_UPDATE_JOB/{jobId}") { backStackEntry ->
            backStackEntry.arguments
                ?.getString("jobId")
                ?.let { UpdatejobScreen(navController, it) }
        }

        composable(ROUTE_ADD_APPLICATION) {
            AddApplicationScreen(navController)
        }
        composable(ROUTE_APPLICATION_BOARD) {
            ApplicationBoard(navController)
        }
        composable(ROUTE_VIEW_APPLICATION) {
            ViewApplications(navController)
        }
        composable("$ROUTE_UPDATE_APPLICATION/{applicationId}") { backStackEntry ->
            backStackEntry.arguments
                ?.getString("applicationId")
                ?.let { UpdateApplicationScreen(navController, it) }
        }
    }
}

