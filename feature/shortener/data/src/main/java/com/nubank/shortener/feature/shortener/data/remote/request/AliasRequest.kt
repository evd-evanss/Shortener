package com.nubank.shortener.feature.shortener.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class AliasRequest(
    val url: String,
)
