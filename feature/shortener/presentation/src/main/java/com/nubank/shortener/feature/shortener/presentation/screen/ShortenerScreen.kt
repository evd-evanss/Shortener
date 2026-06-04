package com.nubank.shortener.feature.shortener.presentation.screen

import android.content.ClipData
import androidx.compose.animation.AnimatedVisibility
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
import com.nubank.shortener.designsystem.component.NuEmptyState
import com.nubank.shortener.designsystem.component.NuInfoCard
import com.nubank.shortener.designsystem.component.NuLinearProgress
import com.nubank.shortener.designsystem.component.NuPrimaryButton
import com.nubank.shortener.designsystem.component.NuUrlTextField
import com.nubank.shortener.designsystem.component.NuText
import com.nubank.shortener.designsystem.component.NuTextStyle
import com.nubank.shortener.designsystem.theme.NuTheme
import com.nubank.shortener.feature.shortener.domain.model.ShortenedUrl
import com.nubank.shortener.feature.shortener.presentation.R
import com.nubank.shortener.feature.shortener.presentation.screen.states.ShortenerMessage
import com.nubank.shortener.feature.shortener.presentation.screen.states.ShortenerUiState
import com.nubank.shortener.feature.shortener.presentation.screen.states.UiMessage
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            NuText(text = stringResource(R.string.shortener_title), style = NuTextStyle.ScreenTitle)
            NuText(
                text = stringResource(R.string.shortener_subtitle),
                style = NuTextStyle.ScreenSubtitle,
                modifier = Modifier.padding(top = 4.dp),
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NuUrlTextField(
                    modifier = Modifier.weight(1f),
                    value = state.url,
                    onValueChange = onUrlChanged,
                    enabled = !state.isLoading,
                )

                NuPrimaryButton(
                    modifier = Modifier,
                    text = stringResource(R.string.shortener_submit),
                    onClick = {
                        keyboard?.hide()
                        onShortenClick()
                    },
                    enabled = !state.isLoading,
                )
            }

            NuText(
                text = stringResource(R.string.shortener_helper),
                style = NuTextStyle.Helper,
                modifier = Modifier.padding(top = 8.dp),
            )

            AnimatedVisibility(visible = state.isLoading) {
                NuLinearProgress(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                )
            }

            Spacer(Modifier.height(30.dp))

            NuText(text = stringResource(R.string.shortener_recent_title), style = NuTextStyle.SectionTitle)
            Spacer(Modifier.height(12.dp))

            if (state.history.isEmpty()) {
                NuEmptyState(text = stringResource(R.string.shortener_empty_history))
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

    NuInfoCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NuText(
                text = stringResource(R.string.shortener_generated_link_title),
                style = NuTextStyle.CardTitle,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        NuText(
            text = item.alias,
            style = NuTextStyle.CardValue,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
        )
        NuText(
            text = stringResource(R.string.shortener_api_link_label),
            style = NuTextStyle.CardLabel,
            modifier = Modifier.padding(top = 12.dp),
        )
        NuText(
            text = item.shortUrl,
            style = NuTextStyle.CardCaption,
            modifier = Modifier.padding(top = 4.dp),
        )
        NuText(
            text = stringResource(R.string.shortener_original_url_label),
            style = NuTextStyle.CardLabel,
            modifier = Modifier.padding(top = 12.dp),
        )
        NuText(
            text = item.originalUrl,
            style = NuTextStyle.Helper,
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
                NuText(text = stringResource(R.string.shortener_copy_action), style = NuTextStyle.Action)
            }
            TextButton(
                onClick = {
                    onOpenClick(item)
                },
            ) {
                NuText(text = stringResource(R.string.shortener_open_action), style = NuTextStyle.Action)
            }
        }
    }
}


@Preview
@Composable
private fun  ShortenerScreenPreview() {
    NuTheme {
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
