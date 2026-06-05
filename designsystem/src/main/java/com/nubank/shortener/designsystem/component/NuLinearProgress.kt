package com.nubank.shortener.designsystem.component

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
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

@Composable
fun NuLinearProgress(
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
                color = NuColors.Purple,
            )
        }
    }
}

@Preview
@Composable
internal fun NuLinearProgressPreview() {
    NuTheme {
        Surface {
            NuLinearProgress(isLoading = true)
        }
    }
}