package com.shortener.feature.shortener.impl.usecase

import com.shortener.feature.shortener.api.Shortener
import com.shortener.feature.shortener.api.ShortenUrlResult
import com.shortener.feature.shortener.api.model.ShortenerError
import com.shortener.feature.shortener.api.repository.UrlShortenerRepository

class ShortenUrlUseCase(
    private val repository: UrlShortenerRepository,
) : Shortener {
    override suspend fun shorten(rawUrl: String): ShortenUrlResult {
        val normalizedUrl = rawUrl.trim()
        if (normalizedUrl.isBlank()) {
            return ShortenUrlResult.Error(ShortenerError.BlankUrl)
        }

        val validUrl = normalizeScheme(normalizedUrl)
        if (!isValidHttpUrl(validUrl)) {
            return ShortenUrlResult.Error(ShortenerError.InvalidUrl)
        }

        return repository.shorten(validUrl).fold(
            onSuccess = { shortenedUrl ->
                ShortenUrlResult.Success(shortenedUrl)
            },
            onFailure = {
                ShortenUrlResult.Error(
                    ShortenerError.ServiceUnavailable,
                )
            },
        )
    }

    private fun normalizeScheme(url: String): String =
        if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url"

    private fun isValidHttpUrl(url: String): Boolean {
        val pattern = Regex("^https?://[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(/.*)?$")
        return pattern.matches(url)
    }
}
