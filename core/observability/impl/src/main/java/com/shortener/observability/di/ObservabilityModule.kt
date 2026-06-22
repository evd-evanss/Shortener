package com.shortener.observability.di

import com.shortener.observability.logging.logger.AppLogger
import com.shortener.observability.logging.logger.CompositeAppLogger
import com.shortener.observability.logging.report.ConsoleReport
import com.shortener.observability.logging.report.SentryReport
import org.koin.dsl.module

val observabilityModule = module {
    single<AppLogger> {
        CompositeAppLogger(
            reports = listOf(
                ConsoleReport(),
                SentryReport(),
            ),
        )
    }
}
