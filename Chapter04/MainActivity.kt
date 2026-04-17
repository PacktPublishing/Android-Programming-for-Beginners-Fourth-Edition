package com.example.controlflowdemo

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
import com.example.controlflowdemo.ui.theme.ControlFlowDemoTheme

// Add this
import android.util.Log

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ==============================
        // Phase 1: Conditional operators
        // ==============================

        val username = "admin"
        val age = 20

        // Point out that braces can be removed
        if (username == "admin") {
            Log.d("ControlFlowDemo", "Welcome, admin!")
        } else {
            Log.d("ControlFlowDemo", "Access denied")
        }

        if (age >= 18) {
            Log.d("ControlFlowDemo", "User is an adult")
        } else {
            Log.d("ControlFlowDemo", "User is a minor")
        }

        // ==============================
        // Phase 2: Logical operators
        // ==============================

        val isLoggedIn = true
        val isEmailVerified = false

        if (isLoggedIn && isEmailVerified) {
            Log.d("ControlFlowDemo", "User has full access")
        } else {
            Log.d("ControlFlowDemo", "User does not have full access")
        }

        if (isLoggedIn || isEmailVerified) {
            Log.d("ControlFlowDemo", "User can proceed")
        } else {
            Log.d("ControlFlowDemo", "User cannot proceed")
        }

        if (!isLoggedIn) {
            Log.d("ControlFlowDemo", "User is logged out")
        }

        // ==============================
        // Phase 3: Conditional expressions
        // ==============================

        val greetingMessage: String =
            if (username == "admin" && isLoggedIn) {
                "Admin"
            } else if (isLoggedIn) {
                "User"
            } else {
                "Guest"
            }

        Log.d("ControlFlowDemo", "Greeting message resolved to: $greetingMessage")

        // ==============================
        // Compose setup (not the focus yet)
        // ==============================

        enableEdgeToEdge()
        setContent {
            ControlFlowDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                        // Change this to greetingMessage
                        name = greetingMessage,

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Delete this
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ControlFlowDemoTheme {
        Greeting("Android")
    }
}
