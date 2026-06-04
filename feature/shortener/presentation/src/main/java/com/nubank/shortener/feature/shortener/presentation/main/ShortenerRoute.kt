package com.nubank.shortener.feature.shortener.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.nubank.shortener.feature.shortener.presentation.screen.NuSplashScreen
import com.nubank.shortener.feature.shortener.presentation.R
import com.nubank.shortener.feature.shortener.presentation.screen.ShortenerViewModel
import com.nubank.shortener.feature.shortener.presentation.screen.ShortenerScreen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShortenerRoute() {
    val viewModel = koinViewModel<ShortenerViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.showSplash) {
        if (state.showSplash) {
            delay(1_150)
            viewModel.dismissSplash()
        }
    }

    if (state.showSplash) {
        NuSplashScreen(title = stringResource(R.string.shortener_splash_title))
    } else {
        ShortenerScreen(
            state = state,
            onUrlChanged = viewModel::onUrlChanged,
            onShortenClick = viewModel::shorten,
            onOpenClick = viewModel::open,
            onMessageShown = viewModel::dismissMessage,
            onUrlOpened = viewModel::dismissUrlToOpen,
        )
    }
}
