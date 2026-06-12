package com.shortener.feature.shortener.impl.repository

import com.shortener.feature.shortener.impl.remote.AliasRemoteDataSource
import com.shortener.feature.shortener.impl.remote.FakeAliasRemoteDataSource
import com.shortener.feature.shortener.impl.remote.response.AliasLinksResponse
import com.shortener.feature.shortener.impl.remote.response.AliasResponse
import com.shortener.feature.shortener.impl.utils.FakeLogger
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UrlShortenerRepositoryImplTest {
    @Test
    fun `given remote success when shorten then maps response to domain model`() = runBlocking {
        // Given
        val remoteDataSource = FakeAliasRemoteDataSource(
            response = AliasResponse(
                alias = "123456",
                links = AliasLinksResponse(
                    self = "https://example.com",
                    short = "https://url-shortener-server.onrender.com/api/alias/123456",
                ),
            ),
        )
        val repository = createRepository(remoteDataSource)

        // When
        val result = repository.shorten("https://example.com")

        // Then
        assertTrue(result.isSuccess)
        val shortenedUrl = result.getOrThrow()
        assertEquals("https://example.com", shortenedUrl.originalUrl)
        assertEquals("123456", shortenedUrl.alias)
        assertEquals("https://url-shortener-server.onrender.com/api/alias/123456", shortenedUrl.shortUrl)
        assertEquals(listOf("https://example.com"), remoteDataSource.requests)
    }

    @Test
    fun `given remote failure when shorten then returns failure result`() = runBlocking {
        // Given
        val expectedError = IllegalStateException("API unavailable")
        val remoteDataSource = FakeAliasRemoteDataSource(error = expectedError)
        val logger = FakeLogger()
        val repository = createRepository(remoteDataSource, logger)

        // When
        val result = repository.shorten("https://example.com")

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
        assertEquals(listOf("https://example.com"), remoteDataSource.requests)
        assertEquals(1, logger.errors.size)
        assertEquals(expectedError, logger.errors.first().throwable)
    }

    @Test
    fun `given response links when shorten then original url comes from self and short url comes from short`() =
        runBlocking {
            // Given
            val remoteDataSource = FakeAliasRemoteDataSource(
                response = AliasResponse(
                    alias = "abc123",
                    links = AliasLinksResponse(
                        self = "https://original.example/path",
                        short = "https://short.example/abc123",
                    ),
                ),
            )
            val repository = createRepository(remoteDataSource)

            // When
            val result = repository.shorten("https://request.example")

            // Then
            assertTrue(result.isSuccess)
            val shortenedUrl = result.getOrThrow()
            assertEquals("https://original.example/path", shortenedUrl.originalUrl)
            assertEquals("https://short.example/abc123", shortenedUrl.shortUrl)
        }

    private fun createRepository(
        remoteDataSource: AliasRemoteDataSource,
        logger: FakeLogger = FakeLogger(),
    ): UrlShortenerRepositoryImpl {
        return UrlShortenerRepositoryImpl(
            remoteDataSource = remoteDataSource,
            logger = logger,
        )
    }
}