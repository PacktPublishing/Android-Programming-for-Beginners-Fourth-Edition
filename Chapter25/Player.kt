package com.example.spaceshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.BitmapFactory
import androidx.core.graphics.scale

class Player(context: Context,
             private val screenX: Int,
             screenY: Int) {

    // The player will be represented by a Bitmap
    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.player)

    // How wide and high our player will be
    val width = screenX / 20f
    val height = width

    // This keeps track of where the player is
    val position = RectF(
        screenX / 2f,
        screenY-height,
        screenX / 2 + width,
        screenY.toFloat())

    // The pixels per second speed
    private val speed  = 450f

    enum class MovementDirection {
        STOPPED,
        LEFT,
        RIGHT
    }

    var moving = MovementDirection.STOPPED

    init{
        // stretch the bitmap to a size
        // appropriate for the screen resolution
        bitmap = bitmap.scale(width.toInt(), height.toInt(), false)
    }

    // This update function will be called from update in
    // SpaceShooterView It determines if the player's
    // ship needs to move and changes the coordinates
    fun update(fps: Long) {
        // Move as long as it doesn't try and leave the screen
        if (moving == MovementDirection.LEFT && position.left > 0) {
            position.left -= speed / fps
        }

        else if (moving == MovementDirection.RIGHT && position.left < screenX - width) {
            position.left += speed / fps
        }

        position.right = position.left + width
    }
}