package com.example.spaceshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import kotlin.random.Random

class Alien(context: Context, screenWidth: Int, screenHeight: Int) {
    // How wide and high are the aliens
    var width = screenWidth / 35f
    private var height = width

    var position = RectF(
        0f,
        screenHeight + 1f, // Triggers automatic respawning somewhere random
        width,
        screenHeight + 1f + height
    )

    private val startingSpeed : Float = screenHeight / 5f // 5 seconds from top to bottom
    private var speed: Float = startingSpeed

    var isVisible = true

    companion object {
        // The alien ship will be represented by a Bitmap
        var bitmap: Bitmap? = null
    }

    init {
        // Initialize the bitmaps
        bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.alien)

        // stretch the first bitmap to a size
        // appropriate for the screen resolution
        bitmap = bitmap!!.scale((width.toInt()), (height.toInt()), false)
    }

    fun respawn(screenWidth: Int, screenHeight : Int) {
        // Horizontal: anywhere on screen
        val randomX = kotlin.random.Random.nextFloat() * (screenWidth - width)

        // Vertical: somewhere between just off top and one screen height above
        val minY = -height
        val maxY = -height - screenHeight
        val randomY = Random.nextFloat() * (maxY - minY) + minY

        position.left = randomX
        position.right = randomX + width
        position.top = randomY
        position.bottom = randomY + height

        speed = startingSpeed * (0.75f + Random.nextFloat() * 0.5f)
    }

    fun update(fps: Long) {
        position.top += speed/fps
        position.bottom += speed/fps
    }
}