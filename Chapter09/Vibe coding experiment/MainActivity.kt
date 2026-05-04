package com.example.rememberbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {

    val counts = remember { mutableStateListOf(0, 0, 0, 0, 0, 0) }

    var trigger by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF4F6FA)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Button Counter Dashboard",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            // Buttons grid (no weight used)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                for (row in 0 until 3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (col in 0 until 2) {
                            val index = row * 2 + col

                            FancyButton(
                                number = index + 1,
                                onClick = {
                                    counts[index]++
                                    trigger++
                                }
                            )
                        }
                    }
                }
            }

            AnimatedTable(counts = counts, trigger = trigger)
        }
    }
}

@Composable
fun FancyButton(number: Int, onClick: () -> Unit) {

    val colors = listOf(
        Color(0xFF6C63FF),
        Color(0xFF00BFA6),
        Color(0xFFFF6F61),
        Color(0xFFFFC107),
        Color(0xFF42A5F5),
        Color(0xFFAB47BC)
    )

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors[(number - 1) % colors.size],
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(150.dp)
            .height(70.dp)
    ) {
        Text(
            text = "Button $number",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AnimatedTable(counts: List<Int>, trigger: Int) {

    val scale by animateFloatAsState(
        targetValue = if (trigger % 2 == 0) 1f else 1.03f,
        animationSpec = tween(200),
        label = "scale"
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = "Click Counts",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF444444)
            )

            Divider(color = Color.LightGray)

            counts.forEachIndexed { index, count ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Button ${index + 1}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = count.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6C63FF)
                    )
                }
            }
        }
    }
}