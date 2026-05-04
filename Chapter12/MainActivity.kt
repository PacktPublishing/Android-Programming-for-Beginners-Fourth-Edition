package com.example.mapdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PriceListApp()
            }
        }
    }
}

@Composable
fun PriceListApp() {

    var priceMap by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        InputSection(priceMap) { newMap ->
            priceMap = newMap
        }

        Spacer(modifier = Modifier.height(24.dp))
        ListHeader()
        Spacer(modifier = Modifier.height(8.dp))
        PriceList(priceMap)
    }
}

@Composable
fun InputSection(
    currentMap: Map<String, Double>,
    onMapUpdate: (Map<String, Double>) -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Price List (Map Demo)",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = itemPrice,
            onValueChange = { itemPrice = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            val price = itemPrice.toDoubleOrNull()
            if (itemName.isNotBlank() && price != null) {
                // Create a new map to trigger recomposition
                val newMap = currentMap.toMutableMap()
                newMap[itemName] = price
                onMapUpdate(newMap)

                itemName = ""
                itemPrice = ""
            }
        }) {
            Text("Add")
        }
    }
}

@Composable
fun ListHeader() {
    Text(
        text = "Items:",
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun PriceList(priceMap: Map<String, Double>) {
    Column {
        for ((name, price) in priceMap) {
            Text(text = "$name : $price")
        }
    }
}