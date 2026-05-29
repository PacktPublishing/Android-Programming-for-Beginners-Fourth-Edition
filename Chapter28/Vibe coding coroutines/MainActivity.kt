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
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                isLoading = true
                text = "Working..."
                delay(2000)
                text = "Done!"
                isLoading = false
            }
        },
        enabled = !isLoading
    ) {
        Text(text)
    }
}

/*
Why this fixes the issue:

1.Prevents Race Conditions: Without the check, every button click launches a new coroutine. If you click three times, you'll have three coroutines waiting to update the text state. This can lead to "Done!" appearing and then being immediately overwritten by "Working..." from a later-starting task.

2.State Synchronization: By using isLoading to disable the Button, we ensure that only one coroutine can be active at a time. The button won't accept new clicks until the current coroutine sets isLoading = false at the end of its block.

3.Visual Feedback: Disabling the button provides immediate feedback to the user that their action has been received and is being processed, which is a standard UI pattern.
 */
