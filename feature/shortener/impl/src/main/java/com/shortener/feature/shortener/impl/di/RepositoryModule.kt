package com.shortener.feature.shortener.impl.di

import com.shortener.feature.shortener.api.repository.UrlShortenerRepository
import com.shortener.feature.shortener.impl.remote.AliasApiClient
import com.shortener.feature.shortener.impl.repository.UrlShortenerRepositoryImpl
import org.koin.dsl.module

val shortenerRepositoryModule = module {
    single { AliasApiClient(get(), get()) }
    single<UrlShortenerRepository> {
        val apiClient = get<AliasApiClient>()
        UrlShortenerRepositoryImpl(
            shortenRemote = apiClient::shorten,
            logger = get(),
        )
    }
}
