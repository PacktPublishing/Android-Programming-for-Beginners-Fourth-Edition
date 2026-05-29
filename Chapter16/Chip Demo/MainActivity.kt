package com.example.chipdemo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChipRow()
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipRow() {
    // Example list of options
    val options = listOf("Red", "Green", "Blue", "Yellow", "Purple")

    // Track selected option
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            FilterChip(
                selected = selectedOption == option,
                onClick = { selectedOption = if (selectedOption == option) null else option },
                label = { Text(option) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFFFA500), // orange for selected
                    selectedLabelColor = Color.White
                )
            )
        }
    }

    // Optional: Show the currently selected option
    if (selectedOption != null) {
        Text(
            text = "Selected: $selectedOption",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
    }
}
