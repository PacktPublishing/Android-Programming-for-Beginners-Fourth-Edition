package com.example.listdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.listdemo.ui.theme.ListDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

val shoppingList = listOf("Bread", "Butter", "Milk")

        setContent {
            ListDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    ShoppingList(
                        items = shoppingList,
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}


@Composable
fun ShoppingList(items: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Shopping List",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (item in items) {
            Text(text = "• $item", style = MaterialTheme.typography.bodyLarge)
        }
    }
}