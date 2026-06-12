package com.shortener.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    onFinished: () -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(SPLASH_DURATION_MILLIS)
        onFinished()
    }

    SplashScreen(title = stringResource(R.string.splash_title))
}

private const val SPLASH_DURATION_MILLIS = 1_150L
