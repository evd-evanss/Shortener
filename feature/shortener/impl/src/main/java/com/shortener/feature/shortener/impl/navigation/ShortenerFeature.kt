package com.shortener.feature.shortener.impl.navigation

import com.shortener.feature.share.api.ShareLinkKey
import com.shortener.feature.shortener.impl.ui.main.ShortenerRoute
import com.shortener.feature.shortener.impl.ui.details.ShortenedUrlDetailsRoute
import com.shortener.navigation.api.AppNavKey
import com.shortener.navigation.api.Feature
import com.shortener.navigation.api.featureEntry
import kotlinx.serialization.Serializable

@Serializable
data object ShortenerKey : AppNavKey

@Serializable
data class ShortenedUrlDetailsKey(
    val alias: String,
    val shortUrl: String,
    val originalUrl: String,
) : AppNavKey


object ShortenerFeature : Feature {
    override val entries = listOf(
        featureEntry<ShortenerKey> { _, navigator ->
            ShortenerRoute(
                onShareClick = { shortenedUrl ->
                    navigator.navigate(
                        ShareLinkKey(
                            alias = shortenedUrl.alias,
                            shortUrl = shortenedUrl.shortUrl,
                            originalUrl = shortenedUrl.originalUrl,
                        ),
                    )
                },
            )
        },
        featureEntry<ShortenedUrlDetailsKey> { key, navigator ->
            ShortenedUrlDetailsRoute(
                key = key,
                onBackClick = navigator::pop,
            )
        },
    )
}
