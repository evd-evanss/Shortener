package com.shortener.observability.logging.report

import com.shortener.observability.logging.model.LogEvent

class ConsoleReport(
    private val tag: String = "AppLogs",
) : Report {
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