package com.nubank.shortener.observability.di

import com.nubank.shortener.observability.logging.AppLogger
import com.nubank.shortener.observability.logging.SentryLogger
import org.koin.dsl.module

val observabilityModule = module {
    single<AppLogger> { SentryLogger() }
}
