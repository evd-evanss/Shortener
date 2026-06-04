package com.nubank.shortener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nubank.shortener.designsystem.theme.NuTheme
import com.nubank.shortener.feature.shortener.presentation.main.ShortenerRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NuTheme{
                ShortenerRoute()
            }
        }
    }
}
