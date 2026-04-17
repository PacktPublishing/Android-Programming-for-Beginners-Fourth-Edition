package com.example.whendemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.whendemoapp.ui.theme.WhenDemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhenDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Reuse RangeList function, but now passing a range for days
                    RangeList(
                        range = 1..7,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Reused RangeList function structure
@Composable
fun RangeList(range: IntRange, modifier: Modifier = Modifier) {
    // Map numbers to day names using when
    val itemsList = range.map { day ->
        when (day) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> "Invalid day"
        }
    }

    LazyColumn(modifier = modifier) {
        items(itemsList) { item ->
            Text(text = item)
        }
    }
}

