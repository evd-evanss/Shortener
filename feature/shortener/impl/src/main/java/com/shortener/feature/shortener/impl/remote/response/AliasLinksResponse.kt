package com.shortener.feature.shortener.impl.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class AliasLinksResponse(
    val self: String,
    val short: String,
)

