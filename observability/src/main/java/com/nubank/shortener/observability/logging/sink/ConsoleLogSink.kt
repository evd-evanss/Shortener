package com.nubank.shortener.observability.logging.sink

import com.nubank.shortener.observability.logging.model.LogEvent
import com.nubank.shortener.observability.logging.sink.LogSink

class ConsoleLogSink(
    private val tag: String = "NuLogs",
) : LogSink {
    override fun log(event: LogEvent) {
        printLog(event.message)
        event.attributes
            .takeIf { it.isNotEmpty() }
            ?.let { attributes -> printLog("attributes=$attributes") }
        event.throwable?.printStackTrace()
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
}