package com.shortener.feature.shortener.api.model

data class ShortenedUrl(
    val originalUrl: String,
    val alias: String,
    val shortUrl: String,
)
