package com.shortener.feature.shortener.impl.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AliasResponse(
    val alias: String,
    @SerialName("_links")
    val links: AliasLinksResponse,
) {
    val originalUrl: String
        get() = links.self

    val shortUrl: String
        get() = links.short
}