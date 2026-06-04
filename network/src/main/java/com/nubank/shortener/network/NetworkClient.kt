package com.nubank.shortener.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkClient(
    @PublishedApi internal val httpClient: HttpClient,
) {
    suspend inline fun <reified Request : Any, reified Response : Any> postJson(
        path: String,
        body: Request,
        successStatusCodes: Set<Int> = setOf(200),
    ): Response = mapNetworkExceptions {
        val response = httpClient.post(path) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        if (response.status.value !in successStatusCodes) {
            val responseBody = response.body<String>()
            throw NetworkException.HttpError(response.status.value, responseBody)
        }

        response.body<Response>()
    }

    suspend inline fun <T> mapNetworkExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (exception: NetworkException) {
            throw exception
        } catch (exception: HttpRequestTimeoutException) {
            throw NetworkException.Timeout(exception)
        } catch (exception: SocketTimeoutException) {
            throw NetworkException.Timeout(exception)
        } catch (exception: IOException) {
            throw NetworkException.Connection(exception)
        } catch (exception: Throwable) {
            throw NetworkException.Unexpected(exception)
        }
    }
}
