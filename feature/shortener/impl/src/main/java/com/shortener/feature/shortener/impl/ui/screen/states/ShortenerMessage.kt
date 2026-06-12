package com.shortener.feature.shortener.impl.ui.screen.states

import com.shortener.feature.shortener.impl.R

enum class ShortenerMessage(
    val stringResId: Int,
) {
    Success(R.string.shortener_success_message),
    BlankUrl(R.string.shortener_blank_url_message),
    InvalidUrl(R.string.shortener_invalid_url_message),
    ServiceUnavailable(R.string.shortener_service_unavailable_message),
    UnknownError(R.string.shortener_unknown_error_message),
}
