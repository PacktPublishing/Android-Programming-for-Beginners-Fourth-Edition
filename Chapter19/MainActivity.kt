package com.example.pianodemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PianoScreen()
        }
    }
}


@Composable
fun PianoScreen() {
    val context = LocalContext.current

    val pianoKeys = remember {
        listOf(
            PianoKey("C", R.raw.c_note),
            PianoKey("D", R.raw.d_note, volume = 0.8f),
            PianoKey("E", R.raw.e_note, volume = 0.6f),
            PianoKey("F", R.raw.f_note, volume = 0.4f),
            PianoKey("G", R.raw.g_note, volume = 0.6f),
            PianoKey("A", R.raw.a_note, volume = 0.8f),
            PianoKey("B", R.raw.b_note),
            PianoKey("C2", R.raw.c_high_note)
        )
    }

    Column {
        pianoKeys.forEach { key ->
            Button(
                onClick = { key.play(context) }
            ) {
                Text(text = key.noteName)
            }
        }
    }
}