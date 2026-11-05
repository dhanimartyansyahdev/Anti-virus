package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BlueGlow
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.CyanBright
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.TextPrimary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularGauge(
    progress: Int,
    isScanning: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.toFloat(),
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "gauge_progress"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "gauge_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_angle"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(156.dp)
            .testTag("malware_circular_gauge")
    ) {
        // Elevated inner white disc matching video
        Box(
            modifier = Modifier
                .size(126.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = Color(0x332563EB),
                    ambientColor = Color(0x1F2563EB)
                )
                .background(Color.White, CircleShape)
        )

        Canvas(modifier = Modifier.size(156.dp)) {
            val strokeWidth = 9.dp.toPx()
            val canvasSize = size.width
            val radius = (canvasSize - strokeWidth) / 2f
            val center = Offset(canvasSize / 2f, canvasSize / 2f)

            // Outer subtle background track
            drawCircle(
                color = Color(0xFFE6EDFA),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
            )

            // When scanning, draw a subtle secondary glow arc
            if (isScanning) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Transparent,
                            CyanBright.copy(alpha = 0.3f * pulseAlpha),
                            BlueGlow.copy(alpha = 0.45f * pulseAlpha),
                            Color.Transparent
                        )
                    ),
                    startAngle = rotationAngle,
                    sweepAngle = 140f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth + 6.dp.toPx(), cap = StrokeCap.Round),
                    size = Size(canvasSize - strokeWidth, canvasSize - strokeWidth),
                    topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)
                )
            }

            // Main Active Progress Arc
            if (animatedProgress > 0f) {
                val sweep = (animatedProgress / 100f) * 360f
                val startAngle = -90f // Start from 12 o'clock

                drawArc(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            CyanBright,
                            BlueGlow,
                            BluePrimary
                        )
                    ),
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = Size(canvasSize - strokeWidth, canvasSize - strokeWidth),
                    topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)
                )

                // Bead / glowing indicator dot at the tip of the arc
                val currentRad = Math.toRadians((startAngle + sweep).toDouble())
                val tipX = center.x + radius * cos(currentRad).toFloat()
                val tipY = center.y + radius * sin(currentRad).toFloat()

                // Tip shadow/glow
                drawCircle(
                    color = BlueGlow.copy(alpha = 0.5f),
                    radius = 8.dp.toPx(),
                    center = Offset(tipX, tipY)
                )
                // Tip solid white core
                drawCircle(
                    color = Color.White,
                    radius = 4.dp.toPx(),
                    center = Offset(tipX, tipY)
                )
            }
        }

        // Center Percentage Text (matches video e.g. "0%", "32%", "74%", "100%")
        Text(
            text = "$progress%",
            fontFamily = PlusJakartaSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary,
            letterSpacing = (-0.5).sp
        )
    }
}
