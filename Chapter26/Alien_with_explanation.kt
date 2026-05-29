package com.example.spaceshooter

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

/*
This class represents a single alien enemy in a simple 2D space shooter. Conceptually,
it is responsible for three things: defining the alien’s size and position, spawning it
just off-screen, and updating its movement over time. The movement has two modes:
a straightforward downward fall and a more complex circular “loop” motion.

The alien’s position is stored as a RectF, which is just a rectangle defined by four
floating-point values (left, top, right, bottom). This rectangle is both its location
and its hitbox. When the alien is first created, it is deliberately placed just below
the screen so that the game logic will immediately trigger a respawn and place it
somewhere above the visible area.

Speed is defined in pixels per second, not per frame. The update function divides
speed by fps so that movement is consistent regardless of frame rate. The chosen
starting speed means the alien would take about five seconds to travel from the
top to the bottom of the screen, although each respawn slightly randomizes this
so they don’t all move identically.

Respawning places the alien at a random horizontal position and somewhere above
the visible screen. The vertical range is intentionally wider than just “just above
the top” so aliens can drift into view at slightly different times. When respawned,
looping is disabled and speed is slightly varied.

The interesting part is the looping behaviour, which uses sine and cosine. To
understand this, imagine a circle centered at some point (loopCenterX, loopCenterY)
with a fixed radius. Instead of moving in straight lines, the alien moves along the
edge of this circle.

Sine and cosine are functions that map an angle to a position on a circle:
- cos(angle) gives the horizontal offset from the center
- sin(angle) gives the vertical offset from the center

If you imagine a point moving around a circle:
- angle = 0 places it on the right side
- angle = PI/2 places it at the bottom (in screen coordinates where Y increases downward)
- angle = PI places it on the left
- angle = 2*PI completes a full circle back to the start

By multiplying cos(angle) and sin(angle) by the loop radius, you scale that unit
circle to the desired size. Then by adding the loop center coordinates, you shift
the circle to wherever you want on screen. This is how the alien’s curved path
is generated.

The variable loopRelativeAngle tracks how far around the circle the alien has moved.
Each frame, it increases by a small amount (deltaAngle). That increment is derived
from angular velocity. The relationship used is:

    linear speed = radius * angular velocity

So:

    angular velocity = speed / radius

This ensures that the alien’s movement speed feels consistent whether it is moving
straight down or along a curve. The angle is increased gradually each frame so the
motion appears smooth.

Loop direction determines whether the alien loops left or right. This is handled by
changing how the angle is interpreted. One direction starts from PI and increases,
the other starts from 0 and decreases. This effectively mirrors the circular motion
so the alien can loop in either direction.

While looping, the alien’s rectangular position is continuously updated so that its
center follows the circular path. The rectangle itself is rebuilt each frame by
offsetting from the computed center point.

The loop ends once the angle completes a full rotation (2 * PI). At that point,
the alien returns to normal downward movement.

Outside of looping, the alien simply moves straight down by adding speed/fps to its
top and bottom coordinates. Occasionally, a random check triggers a loop, but only
if certain conditions are met: the alien must be visible on screen, not too close
to the bottom, and there must be enough horizontal space to perform a full loop
without going off-screen. The code checks both left and right possibilities and
chooses a valid direction.

Finally, the bitmap is shared across all alien instances via a companion object,
so it is only loaded and scaled once. This avoids repeated expensive decoding and
keeps memory usage lower.

Overall, the class combines simple linear motion with circular motion generated
through sine and cosine, using angles and a radius to produce smooth, natural-looking
loops without manually defining curved paths.
*/
