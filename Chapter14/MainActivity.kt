package com.example.exploringnullable

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Safe call
        val nicknameSafe: String? = getNickname(2)
        Log.d("SafeCall", "Nickname = $nicknameSafe")
        // Output: Nickname = null

        // Elvis operator
        val nicknameElvis: String = getNickname(2) ?: "Unknown"
        Log.d("Elvis", "Nickname = $nicknameElvis")
        // Output: Nickname = Unknown

        // Nothing - won't compile
        // val nicknameFail: String = getNickname(2)

        // Non null assertion - crashes
        try {
        	val nicknameNull: String = getNickname(2)!!
        	Log.d("Null", "Nickname = $nicknameNull")
        } catch (e: NullPointerException) {
        	Log.d("Null", "Crash! NullPointerException caught")
        }

        // Finally using non null assertion with a valid value
        val validNickname: String = getNickname(1)!!
        Log.d("Valid nickname", "Nickname = $validNickname")
    }

}


fun getNickname(userId: Int): String? {
    return if (userId == 1) "The Admin Guy" else null
}

