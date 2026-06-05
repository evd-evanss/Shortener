package com.nubank.shortener.observability.logging.logger

typealias LogAttributes = Map<String, String>

interface AppLogger {
    fun info(message: String)

    fun info(message: String, attributes: LogAttributes) {
        info(message)
    }

    fun error(message: String, throwable: Throwable? = null)

    fun error(
        message: String,
        throwable: Throwable?,
        attributes: LogAttributes,
    ) {
        error(message, throwable)
    }
}
