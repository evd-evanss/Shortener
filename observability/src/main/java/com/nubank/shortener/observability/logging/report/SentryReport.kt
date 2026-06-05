package com.nubank.shortener.observability.logging.report

import com.nubank.shortener.observability.logging.model.LogEvent
import com.nubank.shortener.observability.logging.model.LogLevel
import io.sentry.Breadcrumb
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.protocol.SentryId

class SentryReport(
    private val tag: String = "NuLogs",
) : Report {
    override fun log(event: LogEvent) {
        when (event.level) {
            LogLevel.Info -> addBreadcrumb(event)
            LogLevel.Error -> captureError(event)
        }
    }

    private fun addBreadcrumb(event: LogEvent) {
        Sentry.addBreadcrumb(
            Breadcrumb().apply {
                category = tag
                level = SentryLevel.INFO
                message = event.message.toBreadcrumbMessage()
                event.attributes.forEach { (key, value) ->
                    setData(key, value)
                }
            },
        )
    }

    private fun captureError(event: LogEvent) {
        val eventId = event.throwable?.let { throwable ->
            Sentry.captureException(throwable) { scope ->
                scope.level = SentryLevel.ERROR
                scope.setTag("logger", tag)
                scope.setExtra("message", event.message)
                event.attributes.forEach { (key, value) ->
                    scope.setExtra(key, value)
                }
            }
        } ?: Sentry.captureMessage(event.message, SentryLevel.ERROR)

        logEventId(eventId)
        Sentry.flush(SENTRY_FLUSH_TIMEOUT_MILLIS)
    }

    private fun logEventId(eventId: SentryId) {
        println("$tag: Sentry eventId=$eventId")
    }

    private fun String.toBreadcrumbMessage(): String {
        val lines = lines()
        if (lines.size == 1) return this

        return "${lines.first()} (${lines.size} lines)"
    }
}

private const val SENTRY_FLUSH_TIMEOUT_MILLIS = 2_000L
