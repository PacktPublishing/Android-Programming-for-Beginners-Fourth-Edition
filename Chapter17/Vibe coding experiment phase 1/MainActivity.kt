package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

/*
    -------------------------
    ViewModel (State + Logic)
    -------------------------
 */
class CalculatorViewModel : ViewModel() {

    var input by mutableStateOf("")
        private set

    var result by mutableStateOf("")
        private set

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
            "⌫" -> {
                if (input.isNotEmpty()) {
                    input = input.dropLast(1)
                }
            }
            "." -> {
                if (canAddDecimal()) {
                    input += "."
                }
            }
            else -> input += value
        }
    }

    private fun canAddDecimal(): Boolean {
        val parts = input.split(Regex("[+\\-*/]"))
        return parts.lastOrNull()?.contains(".") == false
    }
}

/*
    -------------------------
    UI Layer
    -------------------------
 */

@Composable
fun CalculatorScreen() {
    val viewModel = remember { CalculatorViewModel() }
    val input = viewModel.input
    val result = viewModel.result

    CalculatorContent(
        displayText = if (input.isEmpty()) result else input,
        onButtonClick = viewModel::onButtonClick
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
        listOf("0", ".", "=", "+"),
        listOf("C", "⌫")
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        buttons.forEach { row ->
            CalculatorButtonRow(row, onButtonClick)
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
                onClick = { onButtonClick(label) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CalculatorButton(
    button: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(60.dp)
    ) {
        Text(text = button, fontSize = 20.sp)
    }
}

/*
    -------------------------
    Calculation Logic
    -------------------------
 */

fun calculateResult(input: String): String {
    return try {
        val operands = input.split(Regex("[+\\-*/]"))
        val operator = input.find { it in "+-*/" }

        if (operands.size == 2 && operator != null) {
            val num1 = operands[0].toDouble()
            val num2 = operands[1].toDouble()

            when (operator) {
                '+' -> (num1 + num2).toString()
                '-' -> (num1 - num2).toString()
                '*' -> (num1 * num2).toString()
                '/' -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
                else -> "Error"
            }
        } else {
            "Error"
        }
    } catch (e: Exception) {
        "Error"
    }
}