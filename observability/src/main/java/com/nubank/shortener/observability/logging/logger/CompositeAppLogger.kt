package com.nubank.shortener.observability.logging.logger

import com.nubank.shortener.observability.logging.model.LogEvent
import com.nubank.shortener.observability.logging.model.LogLevel
import com.nubank.shortener.observability.logging.sink.LogSink

class CompositeAppLogger(
    private val sinks: List<LogSink>,
) : AppLogger {
    override fun info(message: String) {
        info(message, emptyMap())
    }

    override fun info(message: String, attributes: LogAttributes) {
        log(
            LogEvent(
                level = LogLevel.Info,
                message = message,
                attributes = attributes,
            ),
        )
    }

    override fun error(message: String, throwable: Throwable?) {
        error(message, throwable, emptyMap())
    }

    override fun error(
        message: String,
        throwable: Throwable?,
        attributes: LogAttributes,
    ) {
        log(
            LogEvent(
                level = LogLevel.Error,
                message = message,
                throwable = throwable,
                attributes = attributes,
            ),
        )
    }

    private fun log(event: LogEvent) {
        sinks.forEach { sink ->
            runCatching {
                sink.log(event)
            }
        }
    }
}
