package com.example.chipdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NeonApp()
            }
        }
    }
}

@Composable
fun NeonApp() {
    // Animated gradient background
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val shift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF0F2027),
            Color(0xFF2C5364),
            Color(0xFF00C9FF),
            Color(0xFF92FE9D)
        ),
        start = Offset(shift, 0f),
        end = Offset(shift + 1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        ChipRow()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChipRow() {
    val options = listOf("Red", "Green", "Blue", "Yellow", "Purple")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val colorMap = mapOf(
        "Red" to Color(0xFFFF4D4D),
        "Green" to Color(0xFF00FFB2),
        "Blue" to Color(0xFF4DA6FF),
        "Yellow" to Color(0xFFFFD84D),
        "Purple" to Color(0xFFB266FF)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { option ->

                val isSelected = selectedOption == option
                val color = colorMap[option] ?: Color.White

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.25f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    ),
                    label = ""
                )

                val glowAlpha by animateFloatAsState(
                    targetValue = if (isSelected) 0.9f else 0.3f,
                    animationSpec = tween(500),
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .scale(scale)
                ) {
                    // Glow layer
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        color.copy(alpha = glowAlpha),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                    )

                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedOption =
                                if (selectedOption == option) null else option
                        },
                        label = {
                            Text(
                                option,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = color,
                            containerColor = Color.Black.copy(alpha = 0.6f),
                            selectedLabelColor = Color.Black,
                            labelColor = Color.White
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedContent(
            targetState = selectedOption,
            transitionSpec = {
                fadeIn(tween(300)) with fadeOut(tween(300))
            },
            label = ""
        ) { target ->
            if (target != null) {
                val color = colorMap[target] ?: Color.White

                Text(
                    text = "SELECTED: $target",
                    color = color,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}