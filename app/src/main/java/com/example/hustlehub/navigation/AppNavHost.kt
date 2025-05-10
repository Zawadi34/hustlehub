package com.example.hustlehub.navigation


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hustlehub.models.Application
import com.example.hustlehub.ui.theme.screens.SplashScreen
import com.example.hustlehub.ui.theme.screens.application.ApplicationScreen
import com.example.hustlehub.ui.theme.screens.home.HomeScreen
import com.example.hustlehub.ui.theme.screens.jobdetails.JobScreen
import com.example.hustlehub.ui.theme.screens.jobdetails.UpdatejobScreen
import com.example.hustlehub.ui.theme.screens.jobdetails.ViewJobs
import com.example.hustlehub.ui.theme.screens.login.LoginScreen
import com.example.hustlehub.ui.theme.screens.register.RegisterScreen


private fun AnimatedContentScope.applicationScreen(
    controller: NavHostController,
    string: String?
) {
}

@Composable
fun AppNavHost(navController:NavHostController = rememberNavController(),startDestination:String= ROUTE_SPLASH) {
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
        composable(ROUTE_HOME) { HomeScreen(navController) }
        composable(ROUTE_JOB_DETAILS) { JobScreen(navController) }
        composable(ROUTE_VIEW_JOB) { ViewJobs(navController) }
        composable("$ROUTE_UPDATE_JOB/{jobId}") { passedData ->
            UpdatejobScreen(
                navController, passedData.arguments?.getString("jobId")!!
            )
        }
        composable("$ROUTE_APPLICATION") { passedData ->
            val jobId = passedData.arguments?.getString("jobId")
            applicationScreen(navController, jobId)
        }

    }
}





