package com.example.functionbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.functionbasics.ui.theme.FunctionBasicsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Function results
        val sum = addNumbers(3, 5)
        val repeated = repeatWord("Hi", 3)
        val isEvenResult = isEven(4)

        setContent {
            FunctionBasicsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFF5F7FA)
                ) { padding ->

                    ResultScreen(
                        sum = sum,
                        repeated = repeated,
                        isEven = isEvenResult,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }

    // Function 1
    fun addNumbers(a: Int, b: Int): Int = a + b

    // Function 2
    fun repeatWord(word: String, times: Int): String = word.repeat(times)

    // Function 3
    fun isEven(number: Int): Boolean = number % 2 == 0
}

@Composable
fun ResultScreen(
    sum: Int,
    repeated: String,
    isEven: Boolean,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Function Results",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(800)) +
                    slideInVertically(initialOffsetY = { it / 2 })
        ) {

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ResultItem("Sum", sum.toString(), Color(0xFF4CAF50))
                    ResultItem("Repeated Word", repeated, Color(0xFF2196F3))
                    ResultItem("Is 4 Even?", isEven.toString(), Color(0xFFFF9800))
                }
            }
        }
    }
}

@Composable
fun ResultItem(label: String, value: String, color: Color) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}