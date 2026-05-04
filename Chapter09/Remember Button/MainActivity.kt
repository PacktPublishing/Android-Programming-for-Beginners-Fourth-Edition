package com.example.rememberbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {

                    // Variable to keep track of the count
                    var count1 by remember { mutableStateOf(0) }
                    var count2 by remember { mutableStateOf(0) }

                    // Text to display the count
                    Text(text = "Button Clicked: $count1 times")
                    Text(text = "Button Clicked: $count2 times")

                    // Button 1
                    Button(
                        onClick = {
                            count1 += 1
                        }
                    ) {
                        Text("Click Me")
                    }

                    // Button 2
                    Button(
                        onClick = {
                            count2 += 1
                        }
                    ) {
                        Text("No, Click Me")
                    }
                }
            }

        }
    }
}