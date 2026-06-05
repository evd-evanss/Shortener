package com.nubank.shortener.designsystem.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nubank.shortener.designsystem.theme.NuColors

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