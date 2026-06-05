package com.nubank.shortener.observability.di

import com.nubank.shortener.observability.logging.logger.AppLogger
import com.nubank.shortener.observability.logging.logger.CompositeAppLogger
import com.nubank.shortener.observability.logging.report.ConsoleReport
import com.nubank.shortener.observability.logging.report.SentryReport
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
