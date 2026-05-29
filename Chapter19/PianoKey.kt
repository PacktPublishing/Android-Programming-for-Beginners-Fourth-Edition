package com.example.pianodemo

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class PianoKey(
    val noteName: String,
    private val soundResId: Int
) {

    var volume: Float = 1.0f
        private set

    // init block
    init {
        Log.d("PianoKey", "PianoKey created: $noteName")
    }

    // Secondary constructor
    constructor(
        noteName: String,
        soundResId: Int,
        volume: Float
    ) : this(noteName, soundResId) {
        this.volume = volume.coerceIn(0f, 1f)
    }

    // Public behavior
    fun play(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer.setVolume(volume, volume)
        mediaPlayer.start()
    }


    fun playWithChecks(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)

        if (mediaPlayer == null) {
            Log.e("PianoKey", "MediaPlayer failed for $noteName")
            return
        }

        Log.d("PianoKey", "Playing $noteName")

        mediaPlayer.setVolume(volume, volume)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            it.release()
            Log.d("PianoKey", "Released $noteName")
        }
    }
}