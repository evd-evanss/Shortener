package com.nubank.shortener.feature.shortener.domain.usecase

import com.nubank.shortener.feature.shortener.domain.model.ShortenerError
import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl
import com.nubank.shortener.feature.shortener.domain.repository.UrlShortenerRepository

class ShortenUrlUseCase(
    private val repository: UrlShortenerRepository,
) {
    suspend operator fun invoke(rawUrl: String): ShortenUrlResult {
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

sealed interface ShortenUrlResult {
    data class Success(val value: ShortenedUrl) : ShortenUrlResult
    data class Error(val error: ShortenerError) : ShortenUrlResult
}
