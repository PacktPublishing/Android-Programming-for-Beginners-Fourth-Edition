package com.example.setdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SimpleSetDemo()
            }
        }
    }
}
@Composable
fun SimpleSetDemo() {

    var itemsSet by remember { mutableStateOf(mutableSetOf("Apple", "Banana")) }
    var textInput by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
    ) {

        Text("Simple Mutable Set Demo", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        Text("Current Items:")
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(itemsSet.toList()) { item ->
                Text("• $item")
            }
       }


        Spacer(Modifier.height(16.dp))

        // ----------------------
        // Text input + button
        // ----------------------
        Row {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("Enter item") }
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                val newItem = textInput.trim()
                if (newItem.isNotEmpty() && !itemsSet.contains(newItem)) {

                    // Add item by creating a new Set to trigger recomposition
                    itemsSet = itemsSet.toMutableSet().apply { add(newItem) }
                }

                // Clear input
                textInput = ""
            }) {
                Text("Add")
            }
        }
    }
}

