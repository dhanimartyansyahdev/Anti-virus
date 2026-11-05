package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.OrbCyan
import com.example.ui.theme.OrbLavender
import com.example.ui.theme.OrbPink
import com.example.ui.theme.OrbSky
import com.example.ui.theme.OrbViolet

@Composable
fun AmbientBackground(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_drift")

    val drift1 by infiniteTransition.animateFloat(
        initialValue = -30f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift1"
    )

    val drift2 by infiniteTransition.animateFloat(
        initialValue = 25f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift2"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        // Base canvas gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFF0F4FD),
                    CanvasBackground,
                    Color(0xFFEEF3FC)
                )
            )
        )

        val width = size.width
        val height = size.height

        // Top-Left Soft Lavender Orb
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    OrbLavender.copy(alpha = 0.55f),
                    OrbViolet.copy(alpha = 0.25f),
                    Color.Transparent
                ),
                center = Offset(width * 0.15f + drift1, height * 0.12f + drift2),
                radius = width * 0.55f
            ),
            center = Offset(width * 0.15f + drift1, height * 0.12f + drift2),
            radius = width * 0.55f
        )

        // Top-Right Soft Cyan & Sky Orb
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    OrbCyan.copy(alpha = 0.6f),
                    OrbSky.copy(alpha = 0.3f),
                    Color.Transparent
                ),
                center = Offset(width * 0.88f + drift2, height * 0.16f - drift1),
                radius = width * 0.65f
            ),
            center = Offset(width * 0.88f + drift2, height * 0.16f - drift1),
            radius = width * 0.65f
        )

        // Mid-Right Soft Pastel Violet Orb
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    OrbPink.copy(alpha = 0.35f),
                    OrbLavender.copy(alpha = 0.2f),
                    Color.Transparent
                ),
                center = Offset(width * 0.95f - drift1, height * 0.55f + drift2),
                radius = width * 0.6f
            ),
            center = Offset(width * 0.95f - drift1, height * 0.55f + drift2),
            radius = width * 0.6f
        )

        // Bottom-Left Cyan Ambient Orb
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    OrbSky.copy(alpha = 0.45f),
                    OrbCyan.copy(alpha = 0.2f),
                    Color.Transparent
                ),
                center = Offset(width * 0.1f - drift2, height * 0.85f + drift1),
                radius = width * 0.6f
            ),
            center = Offset(width * 0.1f - drift2, height * 0.85f + drift1),
            radius = width * 0.6f
        )
    }
}
