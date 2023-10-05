package com.alaishat.ahmed.themoviedb.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Composable
fun BallPulseSyncIndicator(
    modifier: Modifier = Modifier,
    ballsColor: Color = MaterialTheme.colorScheme.onPrimary,
    ballSize: Dp = 8.dp
) {
    val animationValues = (1..3).map { index ->
        var animatedValue by remember { mutableStateOf(0f) }
        LaunchedEffect(key1 = Unit) {
            // Delaying using Coroutines
            delay(70L * index)
            animate(
                initialValue = -6f,
                targetValue = 6f,
                animationSpec = infiniteRepeatable(
                    // Remove delay property
                    animation = tween(durationMillis = 300),
                    repeatMode = RepeatMode.Reverse,
                )
            ) { value, _ -> animatedValue = value }
        }
        animatedValue
    }
    Row(modifier) {
        animationValues.forEach { animatedValue ->
            Ball(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .offset(y = animatedValue.dp),
                color = ballsColor,
                size = ballSize,
            )
        }
    }
}

@Composable
fun BallScaleIndicator() {
    // Creates the infinite transition
    val infiniteTransition = rememberInfiniteTransition()
    // Animate from 0f to 1f
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800)
        )
    )
    Ball(
        modifier = Modifier
            .scale(animationProgress)
            .alpha(1 - animationProgress),
    )
}

@Composable
fun Ball(
    modifier: Modifier = Modifier,
    size: Dp = 12.dp,
    color: Color = Color.Gray,
) {
    Canvas(
        modifier = modifier
            .width(size)
            .height(size)
            .clipToBounds()
            .background(color, CircleShape)
    ) {
        val canvasWidth = this.size.width
        val canvasHeight = this.size.height
        drawCircle(
            color = color,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = this.size.minDimension / 4
        )
    }
}