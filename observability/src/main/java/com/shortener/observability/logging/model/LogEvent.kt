package com.shortener.observability.logging.model

import com.shortener.observability.logging.logger.LogAttributes

data class LogEvent(
    val level: LogLevel,
    val message: String,
    val throwable: Throwable? = null,
    val attributes: LogAttributes = emptyMap(),
)

enum class LogLevel {
    Info,
    Error,
}
