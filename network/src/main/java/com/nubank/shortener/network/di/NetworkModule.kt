package com.nubank.shortener.network.di

import com.nubank.shortener.network.NetworkClient
import com.nubank.shortener.network.NetworkConfig
import com.nubank.shortener.observability.logging.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single { NetworkConfig() }
    single { provideHttpClient(get(), get()) }
    single { NetworkClient(get()) }
}

private fun provideHttpClient(
    config: NetworkConfig,
    logger: AppLogger,
): HttpClient = HttpClient(OkHttp) {
    expectSuccess = false

    install(DefaultRequest) {
        url(config.baseUrl)
    }

    install(HttpTimeout) {
        connectTimeoutMillis = config.connectTimeoutMillis
        socketTimeoutMillis = config.socketTimeoutMillis
        requestTimeoutMillis = config.requestTimeoutMillis
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            },
        )
    }

    install(Logging) {
        level = LogLevel.ALL
        this.logger = object : Logger {
            override fun log(message: String) {
                logger.info("HTTP $message")
            }
        }
    }
}
