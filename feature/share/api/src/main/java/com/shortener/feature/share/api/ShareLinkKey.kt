package com.shortener.feature.share.api

import com.shortener.navigation.api.AppNavKey
import kotlinx.serialization.Serializable

@Serializable
data class ShareLinkKey(
    val alias: String,
    val shortUrl: String,
    val originalUrl: String,
) : AppNavKey
