package com.shortener.observability.logging.report

import com.shortener.observability.logging.model.LogEvent

interface Report {
    fun log(event: LogEvent)
}