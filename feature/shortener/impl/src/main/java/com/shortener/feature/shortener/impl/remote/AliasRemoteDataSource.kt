package com.shortener.feature.shortener.impl.remote

import com.shortener.feature.shortener.impl.remote.response.AliasResponse

interface AliasRemoteDataSource {
    suspend fun shorten(url: String): AliasResponse
}
