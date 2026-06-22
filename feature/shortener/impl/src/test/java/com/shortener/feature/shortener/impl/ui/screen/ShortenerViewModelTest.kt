package com.shortener.feature.shortener.impl.ui.screen

import app.cash.turbine.test
import com.shortener.feature.shortener.api.model.ShortenedUrl
import com.shortener.feature.shortener.api.repository.UrlShortenerRepository
import com.shortener.feature.shortener.impl.usecase.ShortenUrlUseCase
import com.shortener.feature.shortener.impl.ui.screen.states.ShortenerMessage
import com.shortener.observability.logging.logger.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class ShortenerViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `given valid url when shorten succeeds then adds url to history and shows success message`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Given
            val repository = FakeRepository()
            val viewModel = createViewModel(repository)
            val textFieldCleaned = ""

            viewModel.state.test {
                awaitItem()
                viewModel.onUrlChanged("example.com")
                assertEquals("example.com", awaitItem().url)

                // When
                viewModel.shorten()

                // Then
                assertTrue(awaitItem().isLoading)
                advanceUntilIdle()

                val successState = awaitItem()
                assertFalse(successState.isLoading)
                assertEquals(textFieldCleaned, successState.url)
                assertEquals(ShortenerMessage.Success, successState.message?.message)
                assertEquals(1, successState.history.size)
                assertEquals("https://example.com", successState.history.first().originalUrl)
                assertEquals(listOf("https://example.com"), repository.shortenedRequests)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given repository failure when shorten then shows service unavailable message`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Given
            val viewModel = createViewModel(FakeRepository(shouldFail = true))

            viewModel.state.test {
                awaitItem()
                viewModel.onUrlChanged("https://example.com")
                awaitItem()

                // When
                viewModel.shorten()

                // Then
                assertTrue(awaitItem().isLoading)
                advanceUntilIdle()

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
                assertEquals(ShortenerMessage.ServiceUnavailable, errorState.message?.message)
                assertTrue(errorState.history.isEmpty())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given invalid url when shorten then shows invalid url message and does not call repository`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Given
            val repository = FakeRepository()
            val viewModel = createViewModel(repository)

            viewModel.state.test {
                awaitItem()
                viewModel.onUrlChanged("just words")
                awaitItem()

                // When
                viewModel.shorten()

                // Then
                assertTrue(awaitItem().isLoading)
                advanceUntilIdle()

                val errorState = awaitItem()
                assertFalse(errorState.isLoading)
                assertEquals(ShortenerMessage.InvalidUrl, errorState.message?.message)
                assertTrue(repository.shortenedRequests.isEmpty())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `given shortened url when open then uses original url already available in state`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // Given
            val repository = FakeRepository()
            val viewModel = createViewModel(repository)
            val shortenedUrl = ShortenedUrl(
                originalUrl = "https://example.com",
                alias = "123",
                shortUrl = "https://url-shortener-server.onrender.com/api/alias/123",
            )

            viewModel.state.test {
                awaitItem()

                // When
                viewModel.open(shortenedUrl)

                // Then
                val openState = awaitItem()
                assertEquals("https://example.com", openState.urlToOpen)
                assertFalse(openState.isLoading)
                assertTrue(repository.shortenedRequests.isEmpty())
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun createViewModel(
        repository: FakeRepository = FakeRepository(),
    ): ShortenerViewModel {
        return ShortenerViewModel(
            shortener = ShortenUrlUseCase(repository),
            logger = FakeLogger(),
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

private class FakeRepository(
    private val shouldFail: Boolean = false,
) : UrlShortenerRepository {
    val shortenedRequests = mutableListOf<String>()

    override suspend fun shorten(url: String): Result<ShortenedUrl> {
        shortenedRequests += url

        if (shouldFail) {
            return Result.failure(IllegalStateException("API unavailable"))
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

private class FakeLogger : AppLogger {
    override fun info(message: String) = Unit
    override fun error(message: String, throwable: Throwable?) = Unit
}
