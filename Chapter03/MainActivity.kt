package com.example.typesdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var playerAge: Int = 18
        val playerMembershipNumber: Int = 5241

        Log.d("TypesDemo: ", "Age = $playerAge")
        Log.d("TypesDemo: ", "Membership = $playerMembershipNumber")

        playerAge = 19

        // Error: Val cannot be reassigned
        // playerMembershipNumber = 9999

        Log.d("TypesDemo: ","Age = $playerAge")
        Log.d("TypesDemo: ","Membership = $playerMembershipNumber")


        // Mathematical operators section

        // Declare 2 Int vals
        val a: Int = 10
        val b: Int = 5

        // Arithmetic operations
        val sum: Int = a + b
        val difference: Int = a - b
        val multiplyResult: Int = a * b
        val divideResult: Int = a / b

        // Print results to logcat
        Log.d("TypesDemo", "Sum = $sum")
        Log.d("TypesDemo","Difference = $difference")
        Log.d("TypesDemo","Product = $multiplyResult")
        Log.d("TypesDemo","Quotient = $divideResult")

        // More advanced operators section
        val tag = "TypesDemo"

        var x: Int = 10
        val y: Int = 3

        x += y    // same as: a = a + b
        Log.d(tag, "x after += : $x")

        x++       // same as: a = a + 1
        Log.d(tag, "x after ++ : $x")

        x -= y    // same as: a = a - b
        Log.d(tag, "x after -= : $x")

        x--       // same as: a = a - 1
        Log.d(tag, "x after -- : $x")

        x *= y    // same as: a = a * b
        Log.d(tag, "x after *= : $x")

        x /= y    // same as: a = a / b
        Log.d(tag, "x after /= : $x")


     }
}
