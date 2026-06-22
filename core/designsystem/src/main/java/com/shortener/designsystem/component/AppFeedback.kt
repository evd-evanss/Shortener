package com.shortener.designsystem.component

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
import com.shortener.designsystem.R
import com.shortener.designsystem.theme.AppColors
import com.shortener.designsystem.theme.AppTheme

@Composable
fun AppFeedback(
    snackbarData: SnackbarData,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    Snackbar(
        modifier = modifier.padding(horizontal = 12.dp),
        containerColor = if (isError) AppColors.Error else AppColors.Success,
        contentColor = Color.White,
    ) {
        AppText(
            text = snackbarData.visuals.message,
            style = AppTextStyle.Body,
            color = Color.White,
        )
    }
}

@Preview(name = "AppFeedback error", showBackground = true)
@Composable
private fun AppFeedbackPreviewError() {
    AppTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            AppFeedback(
                snackbarData = PreviewSnackbarData(
                    message = stringResource(R.string.app_preview_error_message),
                ),
                isError = true,
            )
        }
    }
}

@Preview(name = "AppFeedback success", showBackground = true)
@Composable
private fun AppFeedbackPreviewSuccess() {
    AppTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            AppFeedback(
                snackbarData = PreviewSnackbarData(
                    message = stringResource(R.string.app_preview_success_message),
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
