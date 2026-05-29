package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlin.math.*

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
    ViewModel
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
                result = evaluateExpression(input)
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
                if (canAddDecimal()) input += "."
            }
            "sin", "cos", "tan", "√" -> {
                input += "$value("
            }
            ")" -> input += ")"
            else -> input += value
        }
    }

    private fun canAddDecimal(): Boolean {
        val parts = input.split(Regex("[+\\-*/()]"))
        return parts.lastOrNull()?.contains(".") == false
    }
}

/*
    -------------------------
    UI
    -------------------------
 */

@Composable
fun CalculatorScreen() {
    val viewModel = remember { CalculatorViewModel() }

    val input = viewModel.input
    val result = viewModel.result

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CalculatorDisplay(if (input.isEmpty()) result else input)
            Spacer(modifier = Modifier.height(16.dp))
            CalculatorKeypad(viewModel::onButtonClick)
        }

        Text(
            text = "Vibe-calc 2",
            modifier = Modifier
                .align(Alignment.BottomStart),
            fontSize = 12.sp,
            color = Color.Gray
        )
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
        listOf("sin", "cos", "tan", "√"),
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+"),
        listOf("C", "⌫", "(", ")")
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        onClick = { onButtonClick(label) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOperator = label in listOf("+", "-", "*", "/", "=")
    val isClear = label == "C"

    val colors = when {
        isClear -> ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)) // red
        isOperator -> ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)) // blue
        else -> ButtonDefaults.buttonColors()
    }

    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        colors = colors
    ) {
        Text(text = label, fontSize = 18.sp, color = Color.White)
    }
}

/*
    -------------------------
    Expression Evaluation
    -------------------------
 */

fun evaluateExpression(expression: String): String {
    return try {
        val tokens = tokenize(expression)
        val rpn = toRPN(tokens)
        val result = evalRPN(rpn)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

fun tokenize(expr: String): List<String> {
    val tokens = mutableListOf<String>()
    var number = ""

    var i = 0
    while (i < expr.length) {
        val c = expr[i]

        when {
            c.isDigit() || c == '.' -> {
                number += c
            }
            c.isLetter() -> {
                var func = ""
                while (i < expr.length && expr[i].isLetter()) {
                    func += expr[i]
                    i++
                }
                tokens.add(func)
                i--
            }
            c in "+-*/()" -> {
                if (number.isNotEmpty()) {
                    tokens.add(number)
                    number = ""
                }
                tokens.add(c.toString())
            }
        }
        i++
    }

    if (number.isNotEmpty()) tokens.add(number)

    return tokens
}

fun precedence(op: String): Int = when (op) {
    "+", "-" -> 1
    "*", "/" -> 2
    else -> 3
}

fun toRPN(tokens: List<String>): List<String> {
    val output = mutableListOf<String>()
    val stack = mutableListOf<String>()

    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> output.add(token)
            token in listOf("sin", "cos", "tan", "√") -> stack.add(token)
            token == "(" -> stack.add(token)
            token == ")" -> {
                while (stack.isNotEmpty() && stack.last() != "(") {
                    output.add(stack.removeAt(stack.lastIndex))
                }
                if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex)
            }
            token in listOf("+", "-", "*", "/") -> {
                while (stack.isNotEmpty() &&
                    precedence(stack.last()) >= precedence(token)
                ) {
                    output.add(stack.removeAt(stack.lastIndex))
                }
                stack.add(token)
            }
        }
    }

    while (stack.isNotEmpty()) {
        output.add(stack.removeAt(stack.lastIndex))
    }

    return output
}

fun evalRPN(tokens: List<String>): Double {
    val stack = mutableListOf<Double>()

    for (token in tokens) {
        when {
            token.toDoubleOrNull() != null -> stack.add(token.toDouble())

            token in listOf("+", "-", "*", "/") -> {
                val b = stack.removeAt(stack.lastIndex)
                val a = stack.removeAt(stack.lastIndex)

                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    else -> 0.0
                }
                stack.add(result)
            }

            token == "sin" -> stack.add(sin(stack.removeAt(stack.lastIndex)))
            token == "cos" -> stack.add(cos(stack.removeAt(stack.lastIndex)))
            token == "tan" -> stack.add(tan(stack.removeAt(stack.lastIndex)))
            token == "√" -> stack.add(sqrt(stack.removeAt(stack.lastIndex)))
        }
    }

    return stack.first()
}