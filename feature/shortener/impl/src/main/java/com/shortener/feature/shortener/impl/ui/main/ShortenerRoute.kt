package com.shortener.feature.shortener.impl.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.shortener.feature.shortener.impl.ui.screen.ShortenerScreen
import com.shortener.feature.shortener.impl.ui.screen.ShortenerViewModel
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
