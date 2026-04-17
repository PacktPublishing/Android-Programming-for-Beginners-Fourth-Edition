package com.example.realhelloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.width

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    //Text(text = "Hello, Jetpack!")
                    //Spacer(modifier = Modifier.height(16.dp))
                    //Text(text = "Hello, Jetpack!")
                    RealHelloWorldGreeting()
                    RealHelloWorldGreeting()
                }
            }
        }
    }


    @Composable
    fun RealHelloWorldGreeting()
    {
        Text(text = "Hello, Jetpack!")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Hello, Jetpack!")
    }

}
