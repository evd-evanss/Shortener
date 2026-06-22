package com.shortener.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shortener.designsystem.R
import com.shortener.designsystem.theme.AppColors
import com.shortener.designsystem.theme.AppTheme

@Composable
fun AppInfoCard(
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted) AppColors.PrimarySoft else AppColors.SurfaceSubtle,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            content = content,
        )
    }
}

@Preview(name = "AppInfoCard", showBackground = true)
@Composable
private fun AppInfoCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppInfoCard {
                AppText(text = stringResource(R.string.app_preview_card_title), style = AppTextStyle.CardTitle)
                AppText(
                    text = stringResource(R.string.app_preview_generated_link),
                    style = AppTextStyle.CardValue,
                )
            }
            AppInfoCard(highlighted = true) {
                AppText(text = stringResource(R.string.app_preview_recent_title), style = AppTextStyle.CardTitle)
                AppText(text = stringResource(R.string.app_preview_recent_link), style = AppTextStyle.CardValue)
            }
        }
    }
}
