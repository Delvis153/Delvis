package com.example.delvis.navigation


import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.delvis.ui.theme.screens.SplashScreen
import com.example.delvis.ui.theme.screens.dashboard.DashboardScreen
import com.example.delvis.ui.theme.screens.jobs.AddjobScreen
import com.example.delvis.ui.theme.screens.jobs.UpdatejobScreen
import com.example.delvis.ui.theme.screens.jobs.ViewJobs
import com.example.delvis.ui.theme.screens.login.LoginScreen
import com.example.delvis.ui.theme.screens.applications.AddApplicationScreen
import com.example.delvis.ui.theme.screens.applications.UpdateApplicationScreen
import com.example.delvis.ui.theme.screens.applications.ViewApplications
import com.example.delvis.ui.theme.screens.register.RegisterScreen
import com.example.delvis.ui.theme.screens.requirements.ResumePreviewScreen
import com.example.delvis.ui.theme.screens.requirements.ResumeScreen
import com.example.delvis.ui.theme.screens.requirements.ResumecreationScreen
import com.example.delvis.ui.theme.screens.subscription.SubscriptionScreen

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
        composable(ROUTE_RESUME_PREVIEW) {
            ResumePreviewScreen(navController)
        }
        composable(ROUTE_RESUME) {
            ResumeScreen(navController)
        }
        composable(ROUTE_VIEW_JOBS) {
           ViewJobs(navController)
        }
        composable(ROUTE_ADD_JOB) {
            AddjobScreen(navController)
        }
        composable(
            route = "update_job/{jobId}",
            arguments = listOf(navArgument("jobId") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobId = Uri.decode(backStackEntry.arguments?.getString("jobId") ?: "")
            UpdatejobScreen(navController, jobId)
        }
        composable(
            "update_application/{applicationId}",
            arguments = listOf(navArgument("applicationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val applicationId = Uri.decode(backStackEntry.arguments?.getString("applicationId") ?: "")
            UpdateApplicationScreen(navController, applicationId)
        }


        composable(ROUTE_ADD_APPLICATION) {
            AddApplicationScreen(navController)
        }
        composable(ROUTE_VIEW_APPLICATION) {
            ViewApplications(navController)
        }


        composable(ROUTE_SUBSCRIPTION) {
            SubscriptionScreen(navController)
        }



        composable(ROUTE_RESUME) {
            ResumeScreen(navController)
        }
        composable(ROUTE_RESUME_CREATION) {
            ResumecreationScreen(navController)
        }
        composable(ROUTE_RESUME_PREVIEW) {
            ResumePreviewScreen(navController)
        }
    }
}
