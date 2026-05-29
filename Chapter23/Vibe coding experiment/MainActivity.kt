package com.example.canvasdemo

import android.os.Bundle
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Color
import android.graphics.RadialGradient
import android.widget.ImageView
import androidx.core.graphics.createBitmap

class MainActivity : Activity() {

    // Objects for drawing
    lateinit var myImageView: ImageView
    lateinit var myBlankBitmap: Bitmap
    lateinit var myCanvas: Canvas
    lateinit var myPaint: Paint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the width and height of the drawing
        val widthInPixels = 800
        val heightInPixels = 600

        // Create a Bitmap to draw on
        myBlankBitmap = createBitmap(widthInPixels, heightInPixels)

        // Initialize Canvas with the Bitmap
        myCanvas = Canvas(myBlankBitmap)

        // Initialize the ImageView and Paint
        myImageView = ImageView(this)
        myPaint = Paint()

        // Create a gradient for the sky background
        val skyGradient = LinearGradient(
            0f, 0f, 0f, heightInPixels.toFloat(),
            Color.parseColor("#87CEEB"),  // Light sky blue color
            Color.parseColor("#4682B4"),  // Darker blue for bottom
            Shader.TileMode.MIRROR
        )

        myPaint.shader = skyGradient
        myCanvas.drawRect(0f, 0f, widthInPixels.toFloat(), heightInPixels.toFloat(), myPaint)

        // Draw the sun (with gradient)
        val sunGradient = RadialGradient(
            400f, 250f, 150f,
            Color.YELLOW, Color.parseColor("#FF4500"),
            Shader.TileMode.CLAMP
        )

        myPaint.shader = sunGradient
        myCanvas.drawCircle(400f, 250f, 150f, myPaint)

        // Draw scattered clouds, now with less overlap and more space between them
        myPaint.shader = null  // Remove any gradient for the clouds
        myPaint.color = Color.WHITE

        // New cloud positions and sizes, ensuring they don't overlap with text
        myCanvas.drawOval(100f, 150f, 300f, 250f, myPaint)  // Cloud 1
        myCanvas.drawOval(300f, 100f, 500f, 200f, myPaint)  // Cloud 2
        myCanvas.drawOval(500f, 150f, 700f, 250f, myPaint)  // Cloud 3
        myCanvas.drawOval(600f, 250f, 800f, 350f, myPaint)  // Cloud 4

        // Draw the green land at the bottom
        myPaint.color = Color.parseColor("#2E8B57")  // Sea green color
        myCanvas.drawRect(0f, heightInPixels - 150f, widthInPixels.toFloat(), heightInPixels.toFloat(), myPaint)

        // Draw simple trees on the land
        drawTree(150f, heightInPixels - 120f)  // Tree 1
        drawTree(300f, heightInPixels - 120f)  // Tree 2
        drawTree(500f, heightInPixels - 120f)  // Tree 3
        drawTree(650f, heightInPixels - 120f)  // Tree 4

        // Add the new text: "Reach for the sky" at top left
        myPaint.color = Color.WHITE
        myPaint.textSize = 50f
        myCanvas.drawText("Reach for the sky", 30f, 80f, myPaint)

        // Set the image to the ImageView
        myImageView.setImageBitmap(myBlankBitmap)

        // Set the ImageView as the activity's content view
        setContentView(myImageView)
    }

    // Function to draw a simple tree (tree trunk and a circular canopy)
    private fun drawTree(x: Float, y: Float) {
        // Tree trunk (a brown rectangle)
        myPaint.color = Color.parseColor("#8B4513")  // Brown color
        myCanvas.drawRect(x - 20f, y, x + 20f, y + 100f, myPaint)

        // Tree canopy (green circle)
        myPaint.color = Color.parseColor("#228B22")  // Forest green color
        myCanvas.drawCircle(x, y - 30f, 50f, myPaint)
    }
}