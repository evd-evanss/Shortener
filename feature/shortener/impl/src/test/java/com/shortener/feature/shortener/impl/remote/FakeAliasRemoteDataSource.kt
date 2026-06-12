package com.shortener.feature.shortener.impl.remote

import com.shortener.feature.shortener.impl.remote.response.AliasResponse

internal class FakeAliasRemoteDataSource(
    private val response: AliasResponse? = null,
    private val error: Throwable? = null,
) : AliasRemoteDataSource {
    val requests = mutableListOf<String>()

    override suspend fun shorten(url: String): AliasResponse {
        requests += url
        error?.let { throw it }
        return requireNotNull(response)
    }
}