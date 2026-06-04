package com.nubank.shortener

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nubank.shortener.designsystem.theme.NuTheme
import com.nubank.shortener.navigation.NuNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NuTheme {
                NuNavHost()
            }
        }
    }
}
