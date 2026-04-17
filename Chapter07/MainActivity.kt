package com.example.functionbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.functionbasics.ui.theme.FunctionBasicsTheme
import androidx.compose.material3.SliderDefaults.Track as Track

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Call the functions
        val sum = addNumbers(3, 5)
        val repeated = repeatWord("Hi", 3)
        val isEvenResult = isEven(4)

        // Combine results into one string
        val message = "Sum: $sum | Repeated: $repeated | Is 4 even? $isEvenResult"

        setContent {

            FunctionBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = message,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // Function 1: Add two numbers
    fun addNumbers(a: Int, b: Int): Int {
        return a + b
    }

    // Function 2: Repeat a word
    fun repeatWord(word: String, times: Int): String {
        return word.repeat(times)
    }

    // Function 3: Check if a number is even
    fun isEven(number: Int): Boolean {
        return number % 2 == 0
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FunctionBasicsTheme {
        Greeting("Android")
    }
}