package com.nubank.shortener.feature.shortener.domain.model

data class ShortenedUrl(
    val originalUrl: String,
    val alias: String,
    val shortUrl: String,
)
