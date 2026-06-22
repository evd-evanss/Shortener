package com.shortener.network

data class NetworkConfig(
    val baseUrl: String = "https://url-shortener-server.onrender.com",
    val connectTimeoutMillis: Long = 15_000,
    val socketTimeoutMillis: Long = 30_000,
    val requestTimeoutMillis: Long = 35_000,
)
