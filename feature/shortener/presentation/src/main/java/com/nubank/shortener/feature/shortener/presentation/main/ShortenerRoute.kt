package com.nubank.shortener.feature.shortener.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nubank.shortener.feature.shortener.presentation.screen.ShortenerViewModel
import com.nubank.shortener.feature.shortener.presentation.screen.ShortenerScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShortenerRoute() {
    val viewModel = koinViewModel<ShortenerViewModel>()
    val state by viewModel.state.collectAsState()

    ShortenerScreen(
        state = state,
        onUrlChanged = viewModel::onUrlChanged,
        onShortenClick = viewModel::shorten,
        onOpenClick = viewModel::open,
        onMessageShown = viewModel::dismissMessage,
        onUrlOpened = viewModel::dismissUrlToOpen,
    )
}
