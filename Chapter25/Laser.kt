package com.example.spaceshooter

import android.graphics.RectF

class Laser(screenHeight: Int,
            private val speed: Float = 350f,
            heightModifier: Float = 20f) {

    val position = RectF()
    private val width = 2
    private var height = screenHeight / heightModifier
    var isActive = false

    fun shoot(startX: Float, startY: Float): Boolean {
        if (!isActive) {
            position.left = startX
            position.top = startY
            position.right = position.left + width
            position.bottom = position.top + height
            //heading = direction
            isActive = true
            return true
        }

        // Laser already active
        return false
    }

    fun update(fps: Long) {
        position.top -= speed / fps
        position.bottom = position.top + height
    }
}