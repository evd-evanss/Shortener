package com.shortener.feature.shortener.impl.di

import com.shortener.feature.shortener.impl.remote.AliasApiClient
import com.shortener.feature.shortener.impl.remote.AliasRemoteDataSource
import com.shortener.feature.shortener.impl.repository.UrlShortenerRepositoryImpl
import com.shortener.feature.shortener.impl.repository.UrlShortenerRepository
import org.koin.dsl.module

val shortenerRepositoryModule = module {
    single<AliasRemoteDataSource> { AliasApiClient(get(), get()) }
    single<UrlShortenerRepository> { UrlShortenerRepositoryImpl(get(), get()) }
}
