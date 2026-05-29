package com.example.chipdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import kotlin.math.*
import kotlin.random.Random

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
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LiquidBackground()
        ChipRow()
    }
}

//////////////////////////////////////////////////////////////
// 🌊 LIQUID BLOBS BACKGROUND
//////////////////////////////////////////////////////////////

@Composable
fun LiquidBackground() {
    val transition = rememberInfiniteTransition()

    val t1 by transition.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(6000), RepeatMode.Reverse)
    )

    val t2 by transition.animateFloat(
        1f, 0f,
        infiniteRepeatable(tween(9000), RepeatMode.Reverse)
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFF00C9FF), Color.Transparent)
            ),
            radius = w * (0.4f + t1 * 0.2f),
            center = Offset(w * (0.3f + t1 * 0.2f), h * 0.4f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                listOf(Color(0xFF92FE9D), Color.Transparent)
            ),
            radius = w * (0.5f + t2 * 0.2f),
            center = Offset(w * (0.7f - t2 * 0.2f), h * 0.6f)
        )
    }
}

//////////////////////////////////////////////////////////////
// 💥 PARTICLE SYSTEM
//////////////////////////////////////////////////////////////

data class Particle(
    var position: Offset,
    var velocity: Offset,
    var life: Float,
    val color: Color
)

@Composable
fun ParticleSystem(particles: List<Particle>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach {
            drawCircle(
                color = it.color.copy(alpha = it.life),
                radius = 6f,
                center = it.position
            )
        }
    }
}

//////////////////////////////////////////////////////////////
// 🎯 CHIP ROW WITH PHYSICS
//////////////////////////////////////////////////////////////

@SuppressLint("RestrictedApi")
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

    var touchPoint by remember { mutableStateOf<Offset?>(null) }
    val particles = remember { mutableStateListOf<Particle>() }

    // Particle updater
    LaunchedEffect(Unit) {
        while (true) {
            particles.forEach {
                it.position += it.velocity
                it.life -= 0.02f
            }
            particles.removeAll { it.life <= 0f }
            kotlinx.coroutines.delay(16)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    touchPoint = change.position
                }
            }
    ) {

        ParticleSystem(particles)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, option ->

                    val isSelected = selectedOption == option
                    val color = colorMap[option]!!

                    var offset by remember { mutableStateOf(Offset.Zero) }

                    // Physics orbit effect
                    LaunchedEffect(touchPoint) {
                        touchPoint?.let { tp ->
                            val angle = index * (2 * PI / options.size)
                            val target = Offset(
                                tp.x + cos(angle).toFloat() * 200f,
                                tp.y + sin(angle).toFloat() * 200f
                            )

                            offset = Offset(
                                lerp(offset.x, target.x - 100f, 0.1f),
                                lerp(offset.y, target.y - 100f, 0.1f)
                            )
                        }
                    }

                    val scale by animateFloatAsState(
                        if (isSelected) 1.3f else 1f,
                        spring(),
                        label = ""
                    )

                    Box(
                        modifier = Modifier
                            .offset { androidx.compose.ui.unit.IntOffset(offset.x.toInt(), offset.y.toInt()) }
                            .scale(scale)
                    ) {

                        FilterChip(
                            selected = isSelected,
                            onClick = {

                                selectedOption =
                                    if (selectedOption == option) null else option

                                // 💥 spawn particles
                                repeat(20) {
                                    particles.add(
                                        Particle(
                                            position = Offset(500f, 900f),
                                            velocity = Offset(
                                                Random.nextFloat() * 20f - 10f,
                                                Random.nextFloat() * -20f
                                            ),
                                            life = 1f,
                                            color = color
                                        )
                                    )
                                }
                            },
                            label = {
                                Text(
                                    option,
                                    fontWeight = FontWeight.Bold
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
                    fadeIn() with fadeOut()
                },
                label = ""
            ) {
                it?.let {
                    Text(
                        text = "SELECTED: $it",
                        color = colorMap[it]!!,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}