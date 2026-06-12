package com.shortener.feature.shortener.impl.ui.screen

import android.content.ClipData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shortener.designsystem.component.AppEmptyState
import com.shortener.designsystem.component.AppFeedback
import com.shortener.designsystem.component.AppInfoCard
import com.shortener.designsystem.component.AppLinearProgress
import com.shortener.designsystem.component.AppPrimaryButton
import com.shortener.designsystem.component.AppText
import com.shortener.designsystem.component.AppTextField
import com.shortener.designsystem.component.AppTextStyle
import com.shortener.designsystem.theme.AppTheme
import com.shortener.feature.shortener.api.model.ShortenedUrl
import com.shortener.feature.shortener.impl.R
import com.shortener.feature.shortener.impl.ui.screen.states.ShortenerMessage
import com.shortener.feature.shortener.impl.ui.screen.states.ShortenerUiState
import com.shortener.feature.shortener.impl.ui.screen.states.UiMessage
import kotlinx.coroutines.launch

@Composable
internal fun ShortenerScreen(
    state: ShortenerUiState,
    onUrlChanged: (String) -> Unit,
    onShortenClick: () -> Unit,
    onOpenClick: (ShortenedUrl) -> Unit,
    onMessageShown: () -> Unit,
    onUrlOpened: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current
    val messageText = state.message?.let { stringResource(it.message.stringResId) }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.message, messageText) {
        messageText?.let {
            snackbarHostState.showSnackbar(it)
            onMessageShown()
        }
    }

    LaunchedEffect(state.urlToOpen) {
        state.urlToOpen?.let {
            uriHandler.openUri(it)
            onUrlOpened()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    AppFeedback(
                        snackbarData = snackbarData,
                        isError = state.message?.message?.let { it != ShortenerMessage.Success } ?: false,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            AppText(text = stringResource(R.string.shortener_title), style = AppTextStyle.ScreenTitle)
            AppText(
                text = stringResource(R.string.shortener_subtitle),
                style = AppTextStyle.ScreenSubtitle,
                modifier = Modifier.padding(top = 4.dp),
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppTextField(
                    modifier = Modifier.weight(1f),
                    value = state.url,
                    onValueChange = onUrlChanged,
                    enabled = !state.isLoading,
                )

                AppPrimaryButton(
                    modifier = Modifier,
                    text = stringResource(R.string.shortener_submit),
                    onClick = {
                        keyboard?.hide()
                        onShortenClick()
                    },
                    enabled = !state.isLoading,
                )
            }

            AppLinearProgress(
                isLoading = state.isLoading,
            )

            AppText(
                text = stringResource(R.string.shortener_recent_title),
                style = AppTextStyle.SectionTitle
            )
            Spacer(Modifier.height(12.dp))

            if (state.history.isEmpty()) {
                AppEmptyState(text = stringResource(R.string.shortener_empty_history))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.history) { item ->
                        HistoryItem(
                            item = item,
                            onOpenClick = onOpenClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(
    item: ShortenedUrl,
    onOpenClick: (ShortenedUrl) -> Unit,
) {
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()
    val copyLabel = stringResource(R.string.shortener_copy_action)

    AppInfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppText(
                text = stringResource(R.string.shortener_generated_link_title),
                style = AppTextStyle.CardTitle,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        AppText(
            text = item.alias,
            style = AppTextStyle.CardValue,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
        )
        AppText(
            text = stringResource(R.string.shortener_api_link_label),
            style = AppTextStyle.CardLabel,
            modifier = Modifier.padding(top = 12.dp),
        )
        AppText(
            text = item.shortUrl,
            style = AppTextStyle.CardCaption,
            modifier = Modifier.padding(top = 4.dp),
        )
        AppText(
            text = stringResource(R.string.shortener_original_url_label),
            style = AppTextStyle.CardLabel,
            modifier = Modifier.padding(top = 12.dp),
        )
        AppText(
            text = item.originalUrl,
            style = AppTextStyle.Helper,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 3.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        clipboard.setClipEntry(
                            ClipEntry(
                                ClipData.newPlainText(copyLabel, item.shortUrl),
                            ),
                        )
                    }
                },
            ) {
                AppText(
                    text = stringResource(R.string.shortener_copy_action),
                    style = AppTextStyle.Action
                )
            }
            TextButton(
                onClick = {
                    onOpenClick(item)
                },
            ) {
                AppText(
                    text = stringResource(R.string.shortener_open_action),
                    style = AppTextStyle.Action
                )
            }
        }
    }
}


@Preview
@Composable
private fun ShortenerScreenPreview() {
    AppTheme {
        ShortenerScreen(
            state = ShortenerUiState(
                url = stringResource(R.string.shortener_preview_url),
                isLoading = false,
                message = UiMessage(
                    message = ShortenerMessage.Success,
                ),
                history = listOf()
            ),
            onShortenClick = {},
            onUrlChanged = {},
            onMessageShown = {},
            onOpenClick = {},
            onUrlOpened = {},
        )
    }
}
