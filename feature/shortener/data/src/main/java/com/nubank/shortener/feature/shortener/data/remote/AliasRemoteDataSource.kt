package com.nubank.shortener.feature.shortener.data.remote

import com.nubank.shortener.feature.shortener.data.remote.response.AliasResponse

interface AliasRemoteDataSource {
    suspend fun shorten(url: String): AliasResponse
}
