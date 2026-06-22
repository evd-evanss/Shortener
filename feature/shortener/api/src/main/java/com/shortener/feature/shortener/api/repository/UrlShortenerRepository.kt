package com.shortener.feature.shortener.api.repository

import com.shortener.feature.shortener.api.model.ShortenedUrl

interface UrlShortenerRepository {
    suspend fun shorten(url: String): Result<ShortenedUrl>
}
