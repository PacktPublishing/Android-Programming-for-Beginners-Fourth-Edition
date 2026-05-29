package com.example.soundandclasses

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundButton()
        }
    }
}

@Composable
fun SoundButton() {
    val context = LocalContext.current
    val sound = remember { Sound() }

    val mediaPlayer = remember {
        MediaPlayer.create(context, sound.resId)
    }

    Button(
        onClick = {
            sound.volume = 1.0f   // setter clamps this to 1.0f
            mediaPlayer.setVolume(sound.volume, sound.volume)
            mediaPlayer.start()
        }
    ) {
        Text(sound.label)
    }
}