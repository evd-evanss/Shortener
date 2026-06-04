package com.nubank.shortener.feature.shortener.domain.di

import com.nubank.shortener.feature.shortener.domain.usecase.ShortenUrlUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ShortenUrlUseCase(get()) }
}
