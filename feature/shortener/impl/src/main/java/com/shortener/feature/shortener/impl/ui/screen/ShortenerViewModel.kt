package com.shortener.feature.shortener.impl.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shortener.feature.shortener.api.Shortener
import com.shortener.feature.shortener.api.ShortenUrlResult
import com.shortener.feature.shortener.api.model.ShortenedUrl
import com.shortener.feature.shortener.api.model.ShortenerError
import com.shortener.feature.shortener.impl.ui.screen.states.ShortenerMessage
import com.shortener.feature.shortener.impl.ui.screen.states.ShortenerUiState
import com.shortener.feature.shortener.impl.ui.screen.states.UiMessage
import com.shortener.observability.logging.logger.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShortenerViewModel(
    private val shortener: Shortener,
    private val logger: AppLogger,
) : ViewModel() {
    private val _state = MutableStateFlow(ShortenerUiState())
    val state: StateFlow<ShortenerUiState> = _state.asStateFlow()

    fun onUrlChanged(value: String) {
        _state.update { current ->
            current.copy(url = value.lowercase(), message = null)
        }
    }

    fun shorten() {
        logger.info("User requested shortening")
        _state.update { it.copy(isLoading = true, message = null) }

        viewModelScope.launch {
            when (val result = shortener.shorten(state.value.url)) {
                is ShortenUrlResult.Success -> addToHistory(result.value, null)
                is ShortenUrlResult.Error -> handleError(result.error)
            }
        }
    }

    fun dismissMessage() {
        _state.update { it.copy(message = null) }
    }

    fun open(shortenedUrl: ShortenedUrl) {
        logger.info("User requested opening original URL from cached shortened item")
        _state.update { current ->
            current.copy(
                message = null,
                urlToOpen = shortenedUrl.originalUrl,
            )
        }
    }

    fun dismissUrlToOpen() {
        _state.update { it.copy(urlToOpen = null) }
    }

    private fun addToHistory(shortenedUrl: ShortenedUrl, message: UiMessage?) {
        _state.update { current ->
            current.copy(
                url = "",
                isLoading = false,
                message = message ?: UiMessage(ShortenerMessage.Success),
                history = listOf(shortenedUrl) + current.history.take(9),
            )
        }
    }

    private fun handleError(error: ShortenerError) {
        when (error) {
            ShortenerError.BlankUrl -> setMessage(ShortenerMessage.BlankUrl)
            ShortenerError.InvalidUrl -> setMessage(ShortenerMessage.InvalidUrl)
            ShortenerError.ServiceUnavailable -> setMessage(ShortenerMessage.ServiceUnavailable)
            is ShortenerError.Unknown -> {
                logger.error(
                    message = "Unknown shortening error",
                    attributes = mapOf("message" to error.message),
                    throwable = null,
                )
                setMessage(ShortenerMessage.UnknownError)
            }
        }
    }

    private fun setMessage(message: ShortenerMessage) {
        logger.info(
            message = "Showing message to user",
            attributes = mapOf("message" to message.name),
        )
        _state.update { current ->
            current.copy(isLoading = false, message = UiMessage(message))
        }
    }
}
