package com.nubank.shortener.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nubank.shortener.designsystem.R
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

enum class NuTextStyle {
    ScreenTitle,
    ScreenSubtitle,
    SectionTitle,
    Helper,
    CardTitle,
    CardValue,
    CardLabel,
    CardCaption,
    Action,
    Body,
}

@Composable
fun NuText(
    text: String,
    style: NuTextStyle,
    modifier: Modifier = Modifier,
    color: Color = style.defaultColor(),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier,
        style = style.textStyle(),
    )
}

private fun NuTextStyle.defaultColor(): Color = when (this) {
    NuTextStyle.ScreenTitle,
    NuTextStyle.CardTitle,
    NuTextStyle.Action -> NuColors.Purple

    NuTextStyle.ScreenSubtitle,
    NuTextStyle.CardLabel -> NuColors.TextSecondary

    NuTextStyle.Helper,
    NuTextStyle.CardCaption -> NuColors.TextTertiary

    NuTextStyle.SectionTitle,
    NuTextStyle.CardValue,
    NuTextStyle.Body -> NuColors.TextPrimary
}

private fun NuTextStyle.textStyle(): TextStyle = when (this) {
    NuTextStyle.ScreenTitle -> TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    )

    NuTextStyle.ScreenSubtitle -> TextStyle(fontSize = 16.sp)

    NuTextStyle.SectionTitle -> TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )

    NuTextStyle.Helper -> TextStyle(fontSize = 13.sp)

    NuTextStyle.CardTitle -> TextStyle(fontWeight = FontWeight.Bold)

    NuTextStyle.CardValue -> TextStyle(fontWeight = FontWeight.SemiBold)

    NuTextStyle.CardLabel -> TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
    )

    NuTextStyle.CardCaption -> TextStyle(fontSize = 12.sp)

    NuTextStyle.Action -> TextStyle(fontWeight = FontWeight.SemiBold)

    NuTextStyle.Body -> TextStyle()
}

@Preview(name = "NuText", showBackground = true)
@Composable
private fun NuTextPreview() {
    NuTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            NuText(text = stringResource(R.string.nu_preview_title), style = NuTextStyle.ScreenTitle)
            NuText(
                text = stringResource(R.string.nu_preview_subtitle),
                style = NuTextStyle.ScreenSubtitle,
            )
            NuText(text = stringResource(R.string.nu_preview_section_title), style = NuTextStyle.SectionTitle)
            NuText(
                text = stringResource(R.string.nu_preview_helper),
                style = NuTextStyle.Helper,
            )
            NuText(text = stringResource(R.string.nu_preview_card_title), style = NuTextStyle.CardTitle)
            NuText(text = stringResource(R.string.nu_preview_card_value), style = NuTextStyle.CardValue)
            NuText(text = stringResource(R.string.nu_preview_card_label), style = NuTextStyle.CardLabel)
            NuText(text = stringResource(R.string.nu_preview_card_caption), style = NuTextStyle.CardCaption)
        }
    }
}
