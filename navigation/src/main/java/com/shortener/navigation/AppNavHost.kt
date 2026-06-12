package com.shortener.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shortener.feature.splash.SplashRoute

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    shortenerContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash,
    ) {
        composable(AppRoute.Splash) {
            SplashRoute(
                onFinished = {
                    navController.navigate(AppRoute.Shortener) {
                        popUpTo(AppRoute.Splash) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(AppRoute.Shortener) {
            shortenerContent()
        }
    }
}
