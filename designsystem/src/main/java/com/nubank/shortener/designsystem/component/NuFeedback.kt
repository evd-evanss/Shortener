package com.nubank.shortener.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nubank.shortener.designsystem.R
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

@Composable
fun NuFeedback(
    snackbarData: SnackbarData,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        modifier = modifier.padding(horizontal = 12.dp),
        containerColor = if (isError) NuColors.Error else NuColors.Success,
        contentColor = Color.White,
    ) {
        NuText(
            text = snackbarData.visuals.message,
            style = NuTextStyle.Body,
            color = Color.White,
        )
    }
}

@Preview(name = "NuFeedback error", showBackground = true)
@Composable
private fun NuFeedbackPreviewError() {
    NuTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            NuFeedback(
                snackbarData = PreviewSnackbarData(
                    message = stringResource(R.string.nu_preview_error_message),
                ),
                isError = true,
            )
        }
    }
}

@Preview(name = "NuFeedback success", showBackground = true)
@Composable
private fun NuFeedbackPreviewSuccess() {
    NuTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            NuFeedback(
                snackbarData = PreviewSnackbarData(
                    message = stringResource(R.string.nu_preview_success_message),
                ),
                isError = false,
            )
        }
    }
}

private class PreviewSnackbarData(
    message: String,
) : SnackbarData {
    override val visuals: SnackbarVisuals = PreviewSnackbarVisuals(message = message)

    override fun performAction() = Unit

    override fun dismiss() = Unit
}

private data class PreviewSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
) : SnackbarVisuals
