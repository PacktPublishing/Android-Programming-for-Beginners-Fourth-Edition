package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}


@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun onButtonClick(value: String) {
        when (value) {
            "=" -> {
                result = calculateResult(input)
                input = ""
            }
            "C" -> {
                input = ""
                result = ""
            }
            else -> input += value
        }
    }

    CalculatorContent(
        displayText = input.ifEmpty { result },
        onButtonClick = ::onButtonClick
    )
}


@Composable
private fun CalculatorContent(
    displayText: String,
    onButtonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        CalculatorDisplay(displayText)
        Spacer(modifier = Modifier.height(24.dp))
        CalculatorKeypad(onButtonClick)
    }
}

@Composable
private fun CalculatorDisplay(text: String) {
    Text(
        text = text,
        fontSize = 40.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.End
    )
}

@Composable
private fun CalculatorKeypad(
    onButtonClick: (String) -> Unit
) {
    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )

    Column {
        buttons.forEach { row ->
            CalculatorButtonRow(row, onButtonClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CalculatorButtonRow(
    buttons: List<String>,
    onButtonClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { label ->
            CalculatorButton(
                button = label,
                onClick = { onButtonClick(label) }
            )
        }
    }
}

@Composable
fun CalculatorButton(button: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(60.dp)
            //.fillMaxWidth(0.25f) // Ensures each button takes up 25% of the row width
            .height(60.dp) // Fixed height for buttons
    ) {
        Text(text = button, fontSize = 24.sp)
    }
}

// Simple function to calculate result manually
fun calculateResult(input: String): String {
    return try {
        // Split the input string into numbers and operators
        val operands = input.split(Regex("[+\\-*/]"))
        val operator = input.find { it in "+-*/" }  // Extract the operator

        if (operands.size == 2 && operator != null) {
            val num1 = operands[0].toDouble()
            val num2 = operands[1].toDouble()

            // Perform calculation based on operator
            when (operator) {
                '+' -> (num1 + num2).toString()
                '-' -> (num1 - num2).toString()
                '*' -> (num1 * num2).toString()
                '/' -> if (num2 != 0.0) (num1 / num2).toString() else "Error" // Prevent division by zero
                else -> "Error"
            }
        } else {
            "Error"
        }
    } catch (e: Exception) {
        "Error"
    }
}
