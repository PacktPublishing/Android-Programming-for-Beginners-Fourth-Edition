package com.example.soundandclasses

class Sound {
    val label = "Play Sound"
    val resId = R.raw.sound

    var volume: Float = 1.0f
        set(value) {
            field = value.coerceIn(0f, 1f)
        }
}





