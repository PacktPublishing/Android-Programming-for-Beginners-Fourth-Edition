package com.example.lifecycledemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Lifecycle Demo", "onCreate called")
    }

    override fun onStart() {
        super.onStart()

        Log.d("Lifecycle Demo", "onStart called")
    }

    override fun onResume() {
        super.onResume()

        Log.d("Lifecycle Demo", "onResume called")
        finish()
    }

    override fun onPause() {
        super.onPause()

        Log.d("Lifecycle Demo", "onPause called")
    }

    override fun onStop() {
        super.onStop()

        Log.d("Lifecycle Demo", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("Lifecycle Demo", "onDestroy called")
    }
}