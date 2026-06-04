package com.nubank.shortener.feature.shortener.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class AliasLinksResponse(
    val self: String,
    val short: String,
)

