package com.shortener.feature.shortener.impl.utils

internal data class LoggedError(
    val message: String,
    val throwable: Throwable?,
)