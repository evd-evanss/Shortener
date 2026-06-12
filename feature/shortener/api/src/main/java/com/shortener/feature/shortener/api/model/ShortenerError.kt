package com.shortener.feature.shortener.api.model

sealed interface ShortenerError {
    data object BlankUrl : ShortenerError
    data object InvalidUrl : ShortenerError
    data object ServiceUnavailable : ShortenerError
    data class Unknown(val message: String) : ShortenerError
}
