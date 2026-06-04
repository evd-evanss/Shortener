package com.nubank.shortener.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NuTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = NuColors.Purple,
            secondary = NuColors.PurpleDark,
            surface = NuColors.Surface,
            background = NuColors.Surface,
        ),
        content = content,
    )
}
