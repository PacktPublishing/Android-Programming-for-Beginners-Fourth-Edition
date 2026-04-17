package com.example.iterationdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val TAG = "LoopsDemo"

        Log.d(TAG, "===== WHILE LOOP =====")

        var i = 1
        while (i <= 5) {
            Log.d(TAG, "i = $i")
            i++
        }

        Log.d(TAG, "===== DO-WHILE LOOP (Dice until 6) =====")

        var roll: Int
        do {
            roll = Random.nextInt(1, 7)
            Log.d(TAG, "Rolled: $roll")
        } while (roll != 6)

        Log.d(TAG, "===== FOR LOOP (Blast off) =====")

        for (count in 3 downTo 1) {
            Log.d(TAG, count.toString())
        }
        Log.d(TAG, "Blast off!")

        Log.d(TAG, "===== BREAK WITH LABEL =====")

        outer@ for (x in 1..3) {
            for (y in 1..3) {
                if (y == 2) {
                    Log.d(TAG, "Breaking out of outer loop")
                    break@outer
                }
                Log.d(TAG, "x=$x, y=$y")
            }
        }

        Log.d(TAG, "===== CONTINUE WITH LABEL =====")

        outer@ for (x in 1..3) {
            for (y in 1..3) {
                if (y == 2) {
                    Log.d(TAG, "Skipping to next x")
                    continue@outer
                }
                Log.d(TAG, "x=$x, y=$y")
            }
        }
    }
}