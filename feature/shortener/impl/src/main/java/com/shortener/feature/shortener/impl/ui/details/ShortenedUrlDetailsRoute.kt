package com.shortener.feature.shortener.impl.ui.details

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shortener.designsystem.component.AppInfoCard
import com.shortener.designsystem.component.AppPrimaryButton
import com.shortener.designsystem.component.AppText
import com.shortener.designsystem.component.AppTextStyle
import com.shortener.designsystem.theme.AppTheme
import com.shortener.feature.shortener.impl.R
import com.shortener.feature.shortener.impl.navigation.ShortenedUrlDetailsKey
import kotlinx.coroutines.launch

@Composable
fun ShortenedUrlDetailsRoute(
    key: ShortenedUrlDetailsKey,
    onBackClick: () -> Unit,
) {
    val clipboard = LocalClipboard.current
    val uriHandler = LocalUriHandler.current
    val coroutineScope = rememberCoroutineScope()
    val copyLabel = stringResource(R.string.shortener_copy_action)

    ShortenedUrlDetailsScreen(
        alias = key.alias,
        shortUrl = key.shortUrl,
        originalUrl = key.originalUrl,
        onBackClick = onBackClick,
        onCopyClick = {
            coroutineScope.launch {
                clipboard.setClipEntry(
                    ClipEntry(
                        ClipData.newPlainText(copyLabel, key.shortUrl),
                    ),
                )
            }
        },
        onOpenOriginalClick = {
            uriHandler.openUri(key.originalUrl)
        },
    )
}

@Composable
private fun ShortenedUrlDetailsScreen(
    alias: String,
    shortUrl: String,
    originalUrl: String,
    onBackClick: () -> Unit,
    onCopyClick: () -> Unit,
    onOpenOriginalClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.Start),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_24),
                contentDescription = stringResource(R.string.shortener_details_back_action),
            )
        }

        Spacer(Modifier.height(12.dp))

        AppText(
            text = stringResource(R.string.shortener_details_title),
            style = AppTextStyle.ScreenTitle,
        )
        AppText(
            text = stringResource(R.string.shortener_details_subtitle),
            style = AppTextStyle.ScreenSubtitle,
            modifier = Modifier.padding(top = 4.dp),
        )

        Spacer(Modifier.height(20.dp))

        AppInfoCard {
            DetailField(
                label = stringResource(R.string.shortener_details_alias_label),
                value = alias,
                valueStyle = AppTextStyle.CardValue,
            )
            DetailField(
                label = stringResource(R.string.shortener_api_link_label),
                value = shortUrl,
            )
            DetailField(
                label = stringResource(R.string.shortener_original_url_label),
                value = originalUrl,
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            AppPrimaryButton(
                text = stringResource(R.string.shortener_copy_action),
                onClick = onCopyClick,
                modifier = Modifier.weight(1f),
            )
            AppPrimaryButton(
                text = stringResource(R.string.shortener_details_open_original_action),
                onClick = onOpenOriginalClick,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String,
    valueStyle: AppTextStyle = AppTextStyle.CardCaption,
) {
    AppText(
        text = label,
        style = AppTextStyle.CardLabel,
    )
    AppText(
        text = value,
        style = valueStyle,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 4.dp, bottom = 14.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun ShortenedUrlDetailsScreenPreview() {
    AppTheme {
        ShortenedUrlDetailsScreen(
            alias = stringResource(R.string.shortener_details_preview_alias),
            shortUrl = stringResource(R.string.shortener_details_preview_short_url),
            originalUrl = stringResource(R.string.shortener_preview_url),
            onBackClick = {},
            onCopyClick = {},
            onOpenOriginalClick = {},
        )
    }
}
