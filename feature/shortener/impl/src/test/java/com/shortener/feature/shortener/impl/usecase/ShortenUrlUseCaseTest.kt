package com.shortener.feature.shortener.impl.usecase

import com.shortener.feature.shortener.api.ShortenUrlResult
import com.shortener.feature.shortener.api.model.ShortenerError
import com.shortener.feature.shortener.api.model.ShortenedUrl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShortenUrlUseCaseTest {
    @Test
    fun `given url without scheme when shorten then sends normalized url to dependency`() = runBlocking {
        // Given
        val shortener = FakeShortener()
        val useCase = ShortenUrlUseCase(shortener::shorten)

        // When
        val result = useCase.shorten("example.com")

        // Then
        assertTrue(result is ShortenUrlResult.Success)
        val value = (result as ShortenUrlResult.Success).value
        assertEquals("https://example.com", value.originalUrl)
        assertEquals(listOf("https://example.com"), shortener.shortenedRequests)
    }

    @Test
    fun `given shorten dependency failure when shorten then returns service unavailable`() = runBlocking {
        // Given
        val useCase = ShortenUrlUseCase(FakeShortener(shouldFail = true)::shorten)

        // When
        val result = useCase.shorten("https://example.com")

        // Then
        assertTrue(result is ShortenUrlResult.Error)
        assertEquals(ShortenerError.ServiceUnavailable, (result as ShortenUrlResult.Error).error)
    }

    @Test
    fun `given invalid url when shorten then rejects url and does not call repository`() = runBlocking {
        // Given
        val shortener = FakeShortener()
        val useCase = ShortenUrlUseCase(shortener::shorten)

        // When
        val result = useCase.shorten("just words")

        // Then
        assertTrue(result is ShortenUrlResult.Error)
        assertTrue(shortener.shortenedRequests.isEmpty())
    }

    private class FakeShortener(
        private val shouldFail: Boolean = false,
    ) {
        val shortenedRequests = mutableListOf<String>()

        suspend fun shorten(url: String): Result<ShortenedUrl> {
            shortenedRequests += url

            if (shouldFail) {
                return Result.failure(IllegalStateException())
            }

            return Result.success(
                ShortenedUrl(
                    originalUrl = url,
                    alias = "nu123",
                    shortUrl = "https://short.url/nu123",
                ),
            )
        }
    }
}
