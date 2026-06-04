package com.nubank.shortener.observability.logging

import io.sentry.Breadcrumb
import io.sentry.Sentry
import io.sentry.SentryLevel
import io.sentry.protocol.SentryId

class SentryLogger(
    private val tag: String = "NuLogs",
) : AppLogger {
    override fun info(message: String) {
        Sentry.addBreadcrumb(
            Breadcrumb().apply {
                category = tag
                level = SentryLevel.INFO
                this.message = message.toBreadcrumbMessage()
            },
        )
        printLog(message)
    }

    override fun error(message: String, throwable: Throwable?) {
        printLog(message)
        throwable?.printStackTrace()
        println("$tag: Sentry enabled=${Sentry.isEnabled()}")
        if (throwable != null) {
            val eventId = Sentry.captureException(throwable) { scope ->
                scope.level = SentryLevel.ERROR
                scope.setTag("logger", tag)
                scope.setExtra("message", message)
            }
            logEventId(eventId)
            Sentry.flush(2_000)
        } else {
            val eventId = Sentry.captureMessage(message, SentryLevel.ERROR)
            logEventId(eventId)
            Sentry.flush(2_000)
        }
    }

    private fun logEventId(eventId: SentryId) {
        println("$tag: Sentry eventId=$eventId")
    }

    private fun printLog(message: String) {
        val lines = message.lines()
        if (lines.size == 1) {
            println("$tag: $message")
            return
        }

        println("$tag: ----- log start -----")
        lines.forEach { line ->
            println("$tag: $line")
        }
        println("$tag: ----- log end -----")
    }

    private fun String.toBreadcrumbMessage(): String {
        val lines = lines()
        if (lines.size == 1) return this

        return "${lines.first()} (${lines.size} lines)"
    }
}
