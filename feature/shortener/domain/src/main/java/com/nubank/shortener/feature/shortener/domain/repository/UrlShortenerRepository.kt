package com.nubank.shortener.feature.shortener.domain.repository

import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl

interface UrlShortenerRepository {
    suspend fun shorten(url: String): Result<ShortenedUrl>
}
