package com.example.brokenrecursion

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        brokenRecursion(1)
    }

    fun brokenRecursion(n: Int) {
        Log.d("RecursionError", "Current value: $n")
        brokenRecursion(n + 1)
    }
}
