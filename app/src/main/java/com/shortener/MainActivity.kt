package com.shortener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.shortener.designsystem.theme.AppTheme
import com.shortener.feature.share.impl.navigation.ShareFeature
import com.shortener.feature.shortener.impl.navigation.ShortenerFeature
import com.shortener.feature.shortener.impl.navigation.ShortenerKey
import com.shortener.feature.splash.navigation.SplashFeature
import com.shortener.feature.splash.navigation.SplashKey
import com.shortener.navigation.api.AppNavKey
import com.shortener.navigation.api.AppNavigator
import com.shortener.navigation.api.Feature

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ShortenerNavigation()
            }
        }
    }
}

@Composable
private fun ShortenerNavigation() {
    val backStack = rememberNavBackStack(SplashKey)
    val navigator = remember(backStack) {
        BackStackNavigator(backStack)
    }
    val features = listOf<Feature>(
        SplashFeature(
            onFinished = {
                replace(ShortenerKey)
            },
        ),
        ShareFeature,
        ShortenerFeature,
    )
    val featureEntries = features.flatMap { it.entries }

    NavDisplay(
        backStack = backStack,
        onBack = navigator::pop,
        entryProvider = { key ->
            val appKey = key as AppNavKey
            val entry = featureEntries.firstOrNull { it.accepts(appKey) }

            NavEntry(appKey) {
                entry?.Content(
                    key = appKey,
                    navigator = navigator,
                )
            }
        },
    )
}

private class BackStackNavigator(
    private val backStack: MutableList<NavKey>,
) : AppNavigator {
    override fun navigate(key: AppNavKey) {
        backStack.add(key)
    }

    override fun replace(key: AppNavKey) {
        backStack.clear()
        backStack.add(key)
    }

    override fun pop() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
}
