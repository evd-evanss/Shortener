package com.shortener.feature.shortener.impl.repository

import com.shortener.feature.shortener.api.model.ShortenedUrl
import com.shortener.feature.shortener.api.repository.UrlShortenerRepository
import com.shortener.feature.shortener.impl.remote.response.AliasResponse
import com.shortener.observability.logging.logger.AppLogger

class UrlShortenerRepositoryImpl(
    private val shortenRemote: suspend (String) -> AliasResponse,
    private val logger: AppLogger,
) : UrlShortenerRepository {
    override suspend fun shorten(url: String): Result<ShortenedUrl> = runCatching {
        shortenRemote(url).let { response ->
            logger.info(
                message = "Remote URL shortening succeeded",
                attributes = mapOf("alias" to response.alias),
            )
            ShortenedUrl(
                originalUrl = response.originalUrl,
                alias = response.alias,
                shortUrl = response.shortUrl,
            )
        }
    }.onFailure { throwable ->
        logger.error(
            message = "Remote URL shortening failed: ${throwable.javaClass.simpleName} - ${throwable.message.orEmpty()}",
            throwable = throwable,
            attributes = mapOf("errorType" to throwable.javaClass.simpleName),
        )
    }
}
