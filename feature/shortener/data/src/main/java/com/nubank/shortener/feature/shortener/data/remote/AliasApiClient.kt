package com.nubank.shortener.feature.shortener.data.remote

import com.nubank.shortener.feature.shortener.data.remote.request.AliasRequest
import com.nubank.shortener.feature.shortener.data.remote.response.AliasResponse
import com.nubank.shortener.network.NetworkClient
import com.nubank.shortener.observability.logging.logger.AppLogger

class AliasApiClient(
    private val networkClient: NetworkClient,
    private val logger: AppLogger,
) : AliasRemoteDataSource {
    override suspend fun shorten(url: String): AliasResponse {
        logger.info(
            message = "Shortening URL through remote API",
            attributes = mapOf("path" to "/api/alias"),
        )
        return networkClient.postJson(
            path = "/api/alias",
            body = AliasRequest(url = url),
            successStatusCodes = setOf(201, 200),
        )
    }
}
