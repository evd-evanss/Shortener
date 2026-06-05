package com.nubank.shortener.observability.di

import com.nubank.shortener.observability.logging.logger.AppLogger
import com.nubank.shortener.observability.logging.logger.CompositeAppLogger
import com.nubank.shortener.observability.logging.sink.ConsoleLogSink
import com.nubank.shortener.observability.logging.sink.SentryLogSink
import org.koin.dsl.module

val observabilityModule = module {
    single<AppLogger> {
        CompositeAppLogger(
            sinks = listOf(
                ConsoleLogSink(),
                SentryLogSink(),
            ),
        )
    }
}
