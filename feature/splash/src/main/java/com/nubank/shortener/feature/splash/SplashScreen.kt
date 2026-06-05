package com.nubank.shortener.feature.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nubank.shortener.designsystem.component.NuText
import com.nubank.shortener.designsystem.component.NuTextStyle
import com.nubank.shortener.designsystem.theme.NuColors
import com.nubank.shortener.designsystem.theme.NuTheme

@Composable
internal fun SplashScreen(
    title: String,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "nu-splash")
    val scale by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "nu-scale",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_nu_orbit),
                contentDescription = null,
                modifier = Modifier
                    .size(220.dp)
                    .scale(scale),
            )

            NuText(
                text = title,
                style = NuTextStyle.ScreenTitle,
                color = NuColors.TextPrimary,
            )
        }
    }
}

@Preview(name = "Splash", widthDp = 360, heightDp = 640)
@Composable
private fun SplashPreview() {
    NuTheme {
        SplashScreen(title = stringResource(R.string.splash_title))
    }
}
