package com.shortener.designsystem.component

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
import com.shortener.designsystem.R
import com.shortener.designsystem.theme.AppColors
import com.shortener.designsystem.theme.AppFontFamily
import com.shortener.designsystem.theme.AppTheme

enum class AppTextStyle {
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
fun AppText(
    text: String,
    style: AppTextStyle,
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

private fun AppTextStyle.defaultColor(): Color = when (this) {
    AppTextStyle.ScreenTitle,
    AppTextStyle.CardTitle,
    AppTextStyle.Action -> AppColors.Primary

    AppTextStyle.ScreenSubtitle,
    AppTextStyle.CardLabel -> AppColors.TextSecondary

    AppTextStyle.Helper,
    AppTextStyle.CardCaption -> AppColors.TextTertiary

    AppTextStyle.SectionTitle,
    AppTextStyle.CardValue,
    AppTextStyle.Body -> AppColors.TextPrimary
}

private fun AppTextStyle.textStyle(): TextStyle = when (this) {
    AppTextStyle.ScreenTitle -> TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
    )

    AppTextStyle.ScreenSubtitle -> TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )

    AppTextStyle.SectionTitle -> TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
    )

    AppTextStyle.Helper -> TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
    )

    AppTextStyle.CardTitle -> TextStyle(fontWeight = FontWeight.ExtraBold)

    AppTextStyle.CardValue -> TextStyle(fontWeight = FontWeight.Bold)

    AppTextStyle.CardLabel -> TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
    )

    AppTextStyle.CardCaption -> TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
    )

    AppTextStyle.Action -> TextStyle(fontWeight = FontWeight.Bold)

    AppTextStyle.Body -> TextStyle(fontWeight = FontWeight.Medium)
}.copy(fontFamily = AppFontFamily)

@Preview(name = "AppText", showBackground = true)
@Composable
private fun AppTextPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AppText(text = stringResource(R.string.app_preview_title), style = AppTextStyle.ScreenTitle)
            AppText(
                text = stringResource(R.string.app_preview_subtitle),
                style = AppTextStyle.ScreenSubtitle,
            )
            AppText(text = stringResource(R.string.app_preview_section_title), style = AppTextStyle.SectionTitle)
            AppText(
                text = stringResource(R.string.app_preview_helper),
                style = AppTextStyle.Helper,
            )
            AppText(text = stringResource(R.string.app_preview_card_title), style = AppTextStyle.CardTitle)
            AppText(text = stringResource(R.string.app_preview_card_value), style = AppTextStyle.CardValue)
            AppText(text = stringResource(R.string.app_preview_card_label), style = AppTextStyle.CardLabel)
            AppText(text = stringResource(R.string.app_preview_card_caption), style = AppTextStyle.CardCaption)
        }
    }
}
