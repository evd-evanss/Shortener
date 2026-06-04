package com.nubank.shortener.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nubank.shortener.feature.shortener.presentation.main.ShortenerRoute
import com.nubank.shortener.feature.splash.SplashRoute

@Composable
fun NuNavHost(
    navController: NavHostController = rememberNavController(),
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
            ShortenerRoute()
        }
    }
}
