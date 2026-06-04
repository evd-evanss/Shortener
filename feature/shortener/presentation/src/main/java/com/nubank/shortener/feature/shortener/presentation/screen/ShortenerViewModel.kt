package com.nubank.shortener.feature.shortener.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl
import com.nubank.shortener.feature.shortener.domain.model.ShortenerError
import com.nubank.shortener.feature.shortener.domain.usecase.ShortenUrlResult
import com.nubank.shortener.feature.shortener.domain.usecase.ShortenUrlUseCase
import com.nubank.shortener.feature.shortener.presentation.screen.states.ShortenerMessage
import com.nubank.shortener.feature.shortener.presentation.screen.states.ShortenerUiState
import com.nubank.shortener.feature.shortener.presentation.screen.states.UiMessage
import com.nubank.shortener.observability.logging.AppLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShortenerViewModel(
    private val shortenUrl: ShortenUrlUseCase,
    private val logger: AppLogger,
) : ViewModel() {
    private val _state = MutableStateFlow(ShortenerUiState())
    val state: StateFlow<ShortenerUiState> = _state.asStateFlow()

    fun dismissSplash() {
        _state.update { it.copy(showSplash = false) }
    }

    fun onUrlChanged(value: String) {
        _state.update { current ->
            current.copy(url = value, message = null)
        }
    }

    fun shorten() {
        logger.info("User requested shortening")
        _state.update { it.copy(isLoading = true, message = null) }

        viewModelScope.launch {
            when (val result = shortenUrl(state.value.url)) {
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
                logger.error("Unknown shortening error: ${error.message}")
                setMessage(ShortenerMessage.UnknownError)
            }
        }
    }

    private fun setMessage(message: ShortenerMessage) {
        logger.error("Showing message to user: ${message.name}")
        _state.update { current ->
            current.copy(isLoading = false, message = UiMessage(message))
        }
    }
}
