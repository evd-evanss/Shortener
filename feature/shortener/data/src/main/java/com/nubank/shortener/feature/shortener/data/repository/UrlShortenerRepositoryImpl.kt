package com.nubank.shortener.feature.shortener.data.repository

import com.nubank.shortener.observability.logging.AppLogger
import com.nubank.shortener.feature.shortener.data.remote.AliasRemoteDataSource
import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl
import com.nubank.shortener.feature.shortener.domain.repository.UrlShortenerRepository

class UrlShortenerRepositoryImpl(
    private val remoteDataSource: AliasRemoteDataSource,
    private val logger: AppLogger,
) : UrlShortenerRepository {
    override suspend fun shorten(url: String): Result<ShortenedUrl> = runCatching {
        remoteDataSource.shorten(url).let { response ->
            logger.info("Remote URL shortening succeeded with alias=${response.alias}")
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
        )
    }
}
