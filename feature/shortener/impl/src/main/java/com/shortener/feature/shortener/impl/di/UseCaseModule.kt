package com.shortener.feature.shortener.impl.di

import com.shortener.feature.shortener.api.Shortener
import com.shortener.feature.shortener.impl.usecase.ShortenUrlUseCase
import org.koin.dsl.module

val shortenerUseCaseModule = module {
    factory<Shortener> { ShortenUrlUseCase(get()) }
}
