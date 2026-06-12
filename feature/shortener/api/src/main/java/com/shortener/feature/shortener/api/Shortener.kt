package com.shortener.feature.shortener.api

import com.shortener.feature.shortener.api.model.ShortenedUrl
import com.shortener.feature.shortener.api.model.ShortenerError

interface Shortener {
    suspend fun shorten(rawUrl: String): ShortenUrlResult
}

sealed interface ShortenUrlResult {
    data class Success(val value: ShortenedUrl) : ShortenUrlResult
    data class Error(val error: ShortenerError) : ShortenUrlResult
}
