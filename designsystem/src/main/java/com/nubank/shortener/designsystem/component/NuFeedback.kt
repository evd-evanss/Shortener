package com.nubank.shortener.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nubank.shortener.designsystem.R
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

@Composable
fun NuLinearProgress(modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        color = NuColors.Purple,
    )
}

@Composable
fun NuEmptyState(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 36.dp),
        contentAlignment = Alignment.Center,
    ) {
        NuText(
            text = text,
            style = NuTextStyle.Helper,
        )
    }
}

@Preview(name = "NuFeedback", showBackground = true)
@Composable
private fun NuFeedbackPreview() {
    NuTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            NuLinearProgress()
            NuEmptyState(text = stringResource(R.string.nu_preview_empty_state))
        }
    }
}
