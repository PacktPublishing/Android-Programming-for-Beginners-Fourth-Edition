package com.example.coroutinesdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CoroutineDemo()
        }
    }
}

@Composable
fun CoroutineDemo() {
    var text by remember { mutableStateOf("Press the button as much as you like") }
    val scope = rememberCoroutineScope()

    Button(onClick = {
        scope.launch {
            text = "Working..."
            delay(2000)
            text = "Done!"
        }
    }) {
        Text(text)
    }
}