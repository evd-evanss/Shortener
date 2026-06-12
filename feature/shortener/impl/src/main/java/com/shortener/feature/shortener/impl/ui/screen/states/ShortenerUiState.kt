package com.shortener.feature.shortener.impl.ui.screen.states

import com.shortener.feature.shortener.api.model.ShortenedUrl

data class ShortenerUiState(
    val url: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val history: List<ShortenedUrl> = emptyList(),
    val urlToOpen: String? = null,
)
