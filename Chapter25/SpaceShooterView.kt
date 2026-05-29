package com.example.spaceshooter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.view.SurfaceView
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.edit

class SpaceShooterView(
    context: Context,
    screenWidth: Int, screenHeight: Int)
    : SurfaceView(context),
    Runnable {

    private val width = screenWidth;
    private val height = screenHeight;

    // This is our thread
    private val gameThread = Thread(this)

    // A boolean which we will set and unset
    private var playing = false

    // Game is paused at the start
    private var paused = true

    // A Canvas and a Paint object
    private var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()

    // The player
    private var player: Player = Player(context, width, height)

    // Some Aliens
    private val aliens = ArrayList<Alien>()

    // The player's Laser
    private var laser = Laser(height, 1200f, 40f)

    // Lives
    private var lives = 3
    // The score
    private var score = 0
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "Space Shooter",
        Context.MODE_PRIVATE)

    private var highScore =  prefs.getInt("highScore", 0)

    private fun prepareAliens() {
        // 30 Aliens please
        repeat(30) {
            aliens.add(Alien(context,
                width,
                height))
        }
    }

    override fun run() {
        // This variable tracks how long a frame took
        var fps: Long = 0

        while (playing) {

            // Capture the current time
            val startFrameTime = System.currentTimeMillis()

            // Update the frame
            if (!paused) {
                update(fps)
            }

            // Draw the frame
            draw()

            // Calculate the frame time
            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }
        }
    }

    private fun update(fps: Long) {
        // Update the state of all the game objects
        // Move the player's ship
        player.update(fps)

        // Has the player lost
        var lost = false

        // Update all the aliens
        for (alien in aliens) {
            alien.update(fps)
        }

        // Update the players Laser if active
        if (laser.isActive) {
            laser.update(fps)
        }

        // Has the player's Laser
        // left the screen
        if (laser.position.bottom < 0) {
            laser.isActive =false
        }

        // Has the player's Laser hit an alien
        if (laser.isActive) {

            for (alien in aliens) {

                if (RectF.intersects(laser.position,
                        alien.position)) {

                    //alien.isVisible = false
                    alien.respawn(width, height)
                    laser.isActive = false
                    //Alien.numberOfInvaders --

                    score += 10

                    if(score > highScore){
                        highScore = score
                    }

                    break
                }
                //}
            }
        }

        for (alien in aliens) {
            // Has an alien bumped into the player
            if (RectF.intersects(player.position, alien.position)) {
                lives --
                val height = alien.position.height()
                alien.position.top = -50f
                alien.position.bottom = -50f + height
                // Is it game over?
                if (lives == 0) {
                    lost = true
                    break
                }
                break;
            }

            // Has the alien gone off the bottom of the screen?
            if (alien.position.top > height) {
                alien.respawn(width, height)
            }
        }

        if (lost) {
            paused = true
            lives = 3
            score = 0
            aliens.clear()
            prepareAliens()
        }
    }

    private fun draw() {
        // Make sure our drawing surface is valid or the game will crash
        if (holder.surface.isValid) {
            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawColor(Color.argb(255, 0, 0, 0))

            // Choose the brush color for drawing
            paint.color = Color.argb(255, 0, 255, 0)

            // Draw all the game objects here
            // Now draw the player
            canvas.drawBitmap(player.bitmap, player.position.left,
                player.position.top
                , paint)

            // Draw in yellow for the aliens
            paint.colorFilter = PorterDuffColorFilter(
                Color.argb(255, 255, 255, 0), // bright yellow
                PorterDuff.Mode.SRC_IN
            )

            for (alien in aliens) {
                canvas.drawBitmap(
                    Alien.bitmap!!,
                    alien.position.left,
                    alien.position.top,
                    paint
                )
            }

            // Reset the color filter or EVERYTHING will be yellow
            paint.colorFilter = null

            // Draw the players playerBullet if active
            paint.color = Color.argb(255, 0, 255, 0)

            if (laser.isActive) {
                canvas.drawRect(laser.position, paint)
            }

            // Change the brush color and text size
            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 50f

            // Draw the HUD
            canvas.drawText("Score: $score   Lives: $lives " +
                    "HI: $highScore", 20f, 175f, paint)

            // Draw everything to the screen
            holder.unlockCanvasAndPost(canvas)
        }
    }

    // If lifecycle is paused/stopped
    // then shut down our thread.
    fun pause() {
        playing = false
        try {
            gameThread.join()
        } catch (_: InterruptedException) {
            Log.e("Error:", "joining thread")
        }

        val prefs = context.getSharedPreferences(
            "Space Shooter",
            Context.MODE_PRIVATE)

        val oldHighScore = prefs.getInt("highScore", 0)

        if(highScore > oldHighScore) {
            prefs.edit {
                putInt(
                    "highScore", highScore
                )
            }
        }
    }

    // If app is started then
    // start our thread.
    fun resume() {
        playing = true
        prepareAliens()
        gameThread.start()
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            // Or moved their finger while touching screen
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE-> {
                paused = false

                if (motionEvent.y > height - height / 8) {
                    if (motionEvent.x > width / 2) {
                        player.moving = Player.MovementDirection.RIGHT
                    } else {
                        player.moving = Player.MovementDirection.LEFT
                    }
                }

                if (motionEvent.y < height - height / 8) {
                    // Shot fired
                    laser.shoot(
                        player.position.left + player.width / 2f,
                        player.position.top)
                }
            }

            // Player has removed finger from screen
            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_UP -> {
                if (motionEvent.y > height - height / 10) {
                    player.moving = Player.MovementDirection.STOPPED
                }
            }
        }// End when clause
        return true
    }// En onTouchEvent function
}