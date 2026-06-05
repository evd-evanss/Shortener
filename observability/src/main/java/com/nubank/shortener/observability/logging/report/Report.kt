package com.nubank.shortener.observability.logging.report

import com.nubank.shortener.observability.logging.model.LogEvent

interface Report {
    fun log(event: LogEvent)
}