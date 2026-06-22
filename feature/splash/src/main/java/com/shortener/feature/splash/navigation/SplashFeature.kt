package com.shortener.feature.splash.navigation

import com.shortener.feature.splash.SplashRoute
import com.shortener.navigation.api.AppNavKey
import com.shortener.navigation.api.AppNavigator
import com.shortener.navigation.api.Feature
import com.shortener.navigation.api.featureEntry
import kotlinx.serialization.Serializable

@Serializable
data object SplashKey : AppNavKey

class SplashFeature(
    private val onFinished: AppNavigator.() -> Unit,
) : Feature {
    override val entries = listOf(
        featureEntry<SplashKey> { _, navigator ->
            SplashRoute(
                onFinished = {
                    navigator.onFinished()
                },
            )
        },
    )
}
