package com.nubank.shortener.network

sealed class NetworkException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    class Timeout(cause: Throwable) : NetworkException(
        "Network request timed out: ${cause.javaClass.simpleName} - ${cause.message.orEmpty()}",
        cause,
    )

    class Connection(cause: Throwable) : NetworkException(
        "Network connection failed: ${cause.javaClass.simpleName} - ${cause.message.orEmpty()}",
        cause,
    )

    class HttpError(val statusCode: Int, val responseBody: String) :
        NetworkException("Unexpected HTTP status: $statusCode. Body: ${responseBody.take(500)}")

    class EmptyBody : NetworkException("Network response body is empty")
    class Parsing(message: String, cause: Throwable? = null) : NetworkException(message, cause)
    class Unexpected(cause: Throwable) : NetworkException(
        "Unexpected network error: ${cause.javaClass.simpleName} - ${cause.message.orEmpty()}",
        cause,
    )
}
