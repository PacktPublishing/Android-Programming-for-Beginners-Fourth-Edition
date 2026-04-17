package com.example.loggingdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myString: String = "Hello Logcat"
        val myInt: Int = 42
        val myBool: Boolean = true

        Log.d("Logging Demo", "String value: $myString")
        Log.d("Logging Demo", "Int value: $myInt")
        Log.d("Logging Demo", "Boolean value: $myBool")
    }
}