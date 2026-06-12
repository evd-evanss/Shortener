package com.shortener.feature.shortener.impl.remote

import com.shortener.feature.shortener.impl.remote.request.AliasRequest
import com.shortener.feature.shortener.impl.remote.response.AliasResponse
import com.shortener.network.NetworkClient
import com.shortener.observability.logging.logger.AppLogger

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
