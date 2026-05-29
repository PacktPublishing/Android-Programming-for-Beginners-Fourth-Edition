package com.example.fabdemo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FabSpacerDividerDemo()
        }
    }
}

@Composable
fun FabSpacerDividerDemo() {
    var useDivider by remember { mutableStateOf(false) }
    val items = listOf("Item One", "Item Two", "Item Three")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { useDivider = !useDivider }) {
                Text("Swap") // simple text label
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            items.forEachIndexed { index, item ->
                Text(item)

                if (index < items.lastIndex) {
                    if (useDivider) Divider() else Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
