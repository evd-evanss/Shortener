package com.shortener.feature.share.impl.navigation

import com.shortener.feature.share.api.ShareLinkKey
import com.shortener.feature.share.impl.ui.ShareRoute
import com.shortener.navigation.api.Feature
import com.shortener.navigation.api.featureEntry

object ShareFeature : Feature {
    override val entries = listOf(
        featureEntry<ShareLinkKey> { key, navigator ->
            ShareRoute(
                key = key,
                onBackClick = navigator::pop,
            )
        },
    )
}
