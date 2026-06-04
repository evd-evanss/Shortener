package com.nubank.shortener.feature.shortener.data.remote

import com.nubank.shortener.feature.shortener.data.remote.response.AliasResponse

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