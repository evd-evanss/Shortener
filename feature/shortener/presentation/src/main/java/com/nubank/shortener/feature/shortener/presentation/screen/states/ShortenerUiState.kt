package com.nubank.shortener.feature.shortener.presentation.screen.states

import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl

data class ShortenerUiState(
    val showSplash: Boolean = true,
    val url: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val history: List<ShortenedUrl> = emptyList(),
    val urlToOpen: String? = null,
)