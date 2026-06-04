package com.nubank.shortener.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nubank.shortener.designsystem.R
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

@Composable
fun NuUrlTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String? = null,
) {
    val resolvedPlaceholder = placeholder ?: stringResource(R.string.nu_url_text_field_placeholder)

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        enabled = enabled,
        placeholder = { NuText(text = resolvedPlaceholder, style = NuTextStyle.Body) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Done,
        ),
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
fun NuPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NuColors.Purple),
        modifier = modifier.height(56.dp),
    ) {
        NuText(
            text = text,
            style = NuTextStyle.Action,
            color = NuColors.Surface,
        )
    }
}

@Preview(name = "NuForm", showBackground = true)
@Composable
private fun NuFormPreview() {
    NuTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NuUrlTextField(
                modifier = Modifier.weight(1f),
                value = stringResource(R.string.nu_preview_card_value),
                onValueChange = {},
            )
            NuPrimaryButton(
                text = stringResource(R.string.nu_preview_button),
                onClick = {},
            )
        }
    }
}
