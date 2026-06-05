package com.nubank.shortener.observability.logging.sink

import com.nubank.shortener.observability.logging.model.LogEvent

interface LogSink {
    fun log(event: LogEvent)
}