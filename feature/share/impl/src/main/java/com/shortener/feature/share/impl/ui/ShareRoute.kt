package com.shortener.feature.share.impl.ui

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shortener.designsystem.component.AppInfoCard
import com.shortener.designsystem.component.AppPrimaryButton
import com.shortener.designsystem.component.AppText
import com.shortener.designsystem.component.AppTextStyle
import com.shortener.designsystem.theme.AppTheme
import com.shortener.feature.share.api.ShareLinkKey
import com.shortener.feature.share.impl.R
import kotlinx.coroutines.launch

@Composable
fun ShareRoute(
    key: ShareLinkKey,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()
    val copyLabel = stringResource(R.string.share_copy_label)
    val shareText = stringResource(
        R.string.share_message,
        key.shortUrl,
        key.originalUrl,
    )
    val chooserTitle = stringResource(R.string.share_chooser_title)

    ShareScreen(
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
        onShareClick = {
            val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, shareText)
            context.startActivity(
                Intent.createChooser(
                    intent,
                    chooserTitle,
                ),
            )
        },
    )
}

@Composable
private fun ShareScreen(
    alias: String,
    shortUrl: String,
    originalUrl: String,
    onBackClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        TextButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.Start),
        ) {
            AppText(
                text = stringResource(R.string.share_back_action),
                style = AppTextStyle.Action,
            )
        }

        Spacer(Modifier.height(12.dp))

        AppText(
            text = stringResource(R.string.share_title),
            style = AppTextStyle.ScreenTitle,
        )
        AppText(
            text = stringResource(R.string.share_subtitle),
            style = AppTextStyle.ScreenSubtitle,
            modifier = Modifier.padding(top = 4.dp),
        )

        Spacer(Modifier.height(20.dp))

        AppInfoCard {
            ShareField(
                label = stringResource(R.string.share_alias_label),
                value = alias,
                valueStyle = AppTextStyle.CardValue,
            )
            ShareField(
                label = stringResource(R.string.share_short_url_label),
                value = shortUrl,
            )
            ShareField(
                label = stringResource(R.string.share_original_url_label),
                value = originalUrl,
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            AppPrimaryButton(
                text = stringResource(R.string.share_copy_action),
                onClick = onCopyClick,
                modifier = Modifier.weight(1f),
            )
            AppPrimaryButton(
                text = stringResource(R.string.share_send_action),
                onClick = onShareClick,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ShareField(
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
private fun ShareScreenPreview() {
    AppTheme {
        ShareScreen(
            alias = stringResource(R.string.share_preview_alias),
            shortUrl = stringResource(R.string.share_preview_short_url),
            originalUrl = stringResource(R.string.share_preview_original_url),
            onBackClick = {},
            onCopyClick = {},
            onShareClick = {},
        )
    }
}
