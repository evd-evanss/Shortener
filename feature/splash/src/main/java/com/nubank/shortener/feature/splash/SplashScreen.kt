package com.nubank.shortener.feature.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    brand: String? = null,
) {
    val resolvedBrand = brand ?: stringResource(R.string.splash_brand)
    val transition = rememberInfiniteTransition(label = "nu-splash")
    val scale by transition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 780, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "nu-scale",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(NuColors.PurpleDark, NuColors.Purple, NuColors.PurpleLight),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .size(104.dp)
                    .scale(scale),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.16f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    NuText(
                        text = resolvedBrand,
                        style = NuTextStyle.ScreenTitle,
                        color = Color.White,
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            NuText(
                text = title,
                style = NuTextStyle.SectionTitle,
                color = Color.White,
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
