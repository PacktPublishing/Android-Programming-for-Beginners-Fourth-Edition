package com.example.spaceshooter

// This is thenhanced (looping) alien


import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class Alien(context: Context, private val screenWidth: Int, private val screenHeight: Int) {
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

    // Looping properties
    private var isLooping = false
    private var loopCenterX = 0f
    private var loopCenterY = 0f
    private val loopRadius = width * 3f
    private var loopRelativeAngle = 0.0
    private var loopDirection = 1 // 1 for right, -1 for left

    companion object {
        // The alien ship will be represented by a Bitmap
        var bitmap: Bitmap? = null
    }

    init {
        // Initialize the bitmaps
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.alien
            )

            // stretch the first bitmap to a size
            // appropriate for the screen resolution
            bitmap = bitmap!!.scale((width.toInt()), (height.toInt()), false)
        }
    }

    fun respawn(screenWidth: Int, screenHeight : Int) {
        // Horizontal: anywhere on screen
        val randomX = Random.nextFloat() * (screenWidth - width)

        // Vertical: somewhere between just off top and one screen height above
        val minY = -height
        val maxY = -height - screenHeight
        val randomY = Random.nextFloat() * (maxY - minY) + minY

        position.left = randomX
        position.right = randomX + width
        position.top = randomY
        position.bottom = randomY + height

        speed = startingSpeed * (0.75f + Random.nextFloat() * 0.5f)
        isLooping = false
    }

    fun update(fps: Long) {
        if (fps <= 0L) return

        if (isLooping) {
            // Angular velocity: v = r * omega => omega = v / r
            val omega = speed / loopRadius
            val deltaAngle = (omega / fps).toDouble()
            loopRelativeAngle += deltaAngle

            // Determine absolute angle based on direction
            // If looping right, start at PI (left side of circle) and move clockwise (+angle)
            // If looping left, start at 0 (right side of circle) and move counter-clockwise (-angle)
            val angle = if (loopDirection > 0) PI + loopRelativeAngle else -loopRelativeAngle

            val currentCenterX = loopCenterX + (loopRadius * cos(angle)).toFloat()
            val currentCenterY = loopCenterY + (loopRadius * sin(angle)).toFloat()

            position.left = currentCenterX - width / 2
            position.top = currentCenterY - height / 2
            position.right = position.left + width
            position.bottom = position.top + height

            // End loop after 360 degrees
            if (loopRelativeAngle >= 2 * PI) {
                isLooping = false
            }
        } else {
            // Standard downward movement
            position.top += speed / fps
            position.bottom += speed / fps

            // Occasional chance to start a loop
            // Conditions:
            // 1. Random chance
            // 2. Must be on screen but not too low (above 60% of screen)
            // 3. Must have enough room on the sides to perform the loop
            if (Random.nextInt(500) == 0 &&
                position.top > height &&
                position.bottom < screenHeight * 0.6f
            ) {
                val canLoopRight = position.right + loopRadius * 2 < screenWidth
                val canLoopLeft = position.left - loopRadius * 2 > 0

                if (canLoopRight || canLoopLeft) {
                    isLooping = true
                    loopRelativeAngle = 0.0
                    
                    if (canLoopRight && (!canLoopLeft || Random.nextBoolean())) {
                        // Loop to the right: Center is to the right of current position
                        loopDirection = 1
                        loopCenterX = position.centerX() + loopRadius
                        loopCenterY = position.centerY()
                    } else {
                        // Loop to the left: Center is to the left of current position
                        loopDirection = -1
                        loopCenterX = position.centerX() - loopRadius
                        loopCenterY = position.centerY()
                    }
                }
            }
        }
    }
}
