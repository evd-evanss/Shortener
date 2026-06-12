package com.shortener.feature.shortener.impl.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class AliasRequest(
    val url: String,
)
