package com.nubank.shortener.feature.shortener.data.utils

import com.nubank.shortener.observability.logging.AppLogger

internal class FakeLogger : AppLogger {
    val errors = mutableListOf<LoggedError>()

    override fun info(message: String) = Unit

    override fun error(message: String, throwable: Throwable?) {
        errors += LoggedError(message, throwable)
    }
}