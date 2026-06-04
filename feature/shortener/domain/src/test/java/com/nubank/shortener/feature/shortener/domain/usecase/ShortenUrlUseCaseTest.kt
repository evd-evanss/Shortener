package com.nubank.shortener.feature.shortener.domain.usecase

import com.nubank.shortener.feature.shortener.domain.model.ShortenerError
import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl
import com.nubank.shortener.feature.shortener.domain.repository.UrlShortenerRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShortenUrlUseCaseTest {
    @Test
    fun `given url without scheme when shorten then sends normalized url to repository`() = runBlocking {
        // Given
        val repository = FakeRepository()
        val useCase = ShortenUrlUseCase(repository)

        // When
        val result = useCase("nubank.com.br")

        // Then
        assertTrue(result is ShortenUrlResult.Success)
        val value = (result as ShortenUrlResult.Success).value
        assertEquals("https://nubank.com.br", value.originalUrl)
        assertEquals(listOf("https://nubank.com.br"), repository.shortenedRequests)
    }

    @Test
    fun `given repository failure when shorten then returns service unavailable`() = runBlocking {
        // Given
        val useCase = ShortenUrlUseCase(FakeRepository(shouldFail = true))

        // When
        val result = useCase("https://nubank.com.br")

        // Then
        assertTrue(result is ShortenUrlResult.Error)
        assertEquals(ShortenerError.ServiceUnavailable, (result as ShortenUrlResult.Error).error)
    }

    @Test
    fun `given invalid url when shorten then rejects url and does not call repository`() = runBlocking {
        // Given
        val repository = FakeRepository()
        val useCase = ShortenUrlUseCase(repository)

        // When
        val result = useCase("just words")

        // Then
        assertTrue(result is ShortenUrlResult.Error)
        assertTrue(repository.shortenedRequests.isEmpty())
    }

    private class FakeRepository(
        private val shouldFail: Boolean = false,
    ) : UrlShortenerRepository {
        val shortenedRequests = mutableListOf<String>()

        override suspend fun shorten(url: String): Result<ShortenedUrl> {
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
