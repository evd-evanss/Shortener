package com.nubank.shortener.feature.shortener.data.utils

internal data class LoggedError(
    val message: String,
    val throwable: Throwable?,
)