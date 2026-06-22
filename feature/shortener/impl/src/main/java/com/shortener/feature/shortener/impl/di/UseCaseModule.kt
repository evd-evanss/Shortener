package com.shortener.feature.shortener.impl.di

import com.shortener.feature.shortener.api.Shortener
import com.shortener.feature.shortener.impl.repository.UrlShortenerRepositoryImpl
import com.shortener.feature.shortener.impl.usecase.ShortenUrlUseCase
import org.koin.dsl.module

val shortenerUseCaseModule = module {
    factory<Shortener> {
        val repository = get<UrlShortenerRepositoryImpl>()
        ShortenUrlUseCase(repository::shorten)
    }
}
