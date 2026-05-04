package com.example.texttolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.texttolist.ui.theme.TextToListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextToListTheme {
                // State management lifted to the MainActivity level
                val list = remember { mutableStateOf(listOf<String>()) }
                val text = remember { mutableStateOf("") }

                // Use Scaffold to structure the layout
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()) {

                        // Display List of Entries
                        ListOfEntries(list.value)

                        // Spacer to add some space between the list and the input fields
                        Spacer(modifier = Modifier.weight(1f))

                        // Bottom section: Text Input and Button
                        TextInputAndButton(
                            text = text.value,
                            onTextChange = { text.value = it },
                            onAddClick = {
                                if (text.value.isNotEmpty()) {
                                    // Add text to the list
                                    list.value = list.value + text.value
                                    text.value = "" // Clear the text input
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ListOfEntries(entries: List<String>) {
    // Display the list with scrolling
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // Scrollable list
            .padding(16.dp) // Padding for the list content
    ) {
        entries.forEach {
            Text(text = it, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Composable
fun TextInputAndButton(
    text: String,
    onTextChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    // Row layout for input and button at the bottom of the screen
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // TextField for input (bottom left)
        TextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text("Enter text") },
            modifier = Modifier
                .fillMaxWidth(0.8f) // Take most of the width
                .padding(end = 16.dp)
        )

        // Button to add the text to the list (bottom right)
        Button(
            onClick = onAddClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text("Add")
        }
    }
}


