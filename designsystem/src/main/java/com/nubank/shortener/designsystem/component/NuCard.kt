package com.nubank.shortener.designsystem.component

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
import com.nubank.shortener.designsystem.R
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

@Composable
fun NuInfoCard(
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (highlighted) NuColors.PurpleSoft else NuColors.SurfaceSubtle,
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

@Preview(name = "NuInfoCard", showBackground = true)
@Composable
private fun NuInfoCardPreview() {
    NuTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            NuInfoCard {
                NuText(text = stringResource(R.string.nu_preview_card_title), style = NuTextStyle.CardTitle)
                NuText(
                    text = stringResource(R.string.nu_preview_generated_link),
                    style = NuTextStyle.CardValue,
                )
            }
            NuInfoCard(highlighted = true) {
                NuText(text = stringResource(R.string.nu_preview_recent_title), style = NuTextStyle.CardTitle)
                NuText(text = stringResource(R.string.nu_preview_recent_link), style = NuTextStyle.CardValue)
            }
        }
    }
}
