package com.shortener.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shortener.designsystem.theme.AppColors
import com.shortener.designsystem.theme.AppTheme

@Composable
fun AppLinearProgress(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Box(
        modifier = modifier.padding(vertical = 14.dp)
    ) {
        AnimatedVisibility(
            visible = isLoading,
        ) {
            LinearProgressIndicator(
                modifier = modifier.fillMaxWidth(),
                color = AppColors.Primary,
            )
        }
    }
}

@Preview
@Composable
internal fun AppLinearProgressPreview() {
    AppTheme {
        Surface {
            AppLinearProgress(isLoading = true)
        }
    }
}