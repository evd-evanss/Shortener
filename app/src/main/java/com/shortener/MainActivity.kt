package com.shortener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shortener.designsystem.theme.AppTheme
import com.shortener.feature.shortener.impl.ui.main.ShortenerRoute
import com.shortener.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavHost(
                    shortenerContent = { ShortenerRoute() },
                )
            }
        }
    }
}
