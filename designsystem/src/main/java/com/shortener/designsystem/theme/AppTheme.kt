package com.shortener.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = AppColors.Primary,
            secondary = AppColors.PrimaryDark,
            surface = AppColors.Surface,
            background = AppColors.Surface,
        ),
        content = content,
    )
}
