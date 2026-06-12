package com.shortener.feature.shortener.impl.utils

import com.shortener.observability.logging.logger.AppLogger

internal class FakeLogger : AppLogger {
    val errors = mutableListOf<LoggedError>()

    override fun info(message: String) = Unit

    override fun error(message: String, throwable: Throwable?) {
        errors += LoggedError(message, throwable)
    }
}