package com.nubank.shortener.feature.shortener.data.di

import com.nubank.shortener.feature.shortener.data.remote.AliasApiClient
import com.nubank.shortener.feature.shortener.data.remote.AliasRemoteDataSource
import com.nubank.shortener.feature.shortener.data.repository.UrlShortenerRepositoryImpl
import com.nubank.shortener.feature.shortener.domain.repository.UrlShortenerRepository
import org.koin.dsl.module

val dataModule = module {
    single<AliasRemoteDataSource> { AliasApiClient(get(), get()) }
    single<UrlShortenerRepository> { UrlShortenerRepositoryImpl(get(), get()) }
}
