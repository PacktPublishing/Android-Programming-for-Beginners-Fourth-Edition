package com.example.spaceshooter

import android.app.Activity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController


class MainActivity : Activity() {

    // spaceShooterView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    private var spaceShooterView: SpaceShooterView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowMetrics = windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val width = bounds.width()
        val height = bounds.height()

        spaceShooterView = SpaceShooterView(this, width, height)
        setContentView(spaceShooterView)

        // Hide the UI
        window.insetsController?.let { controller ->
            controller.hide(
                WindowInsets.Type.statusBars() or
                        WindowInsets.Type.navigationBars()
            )
            controller.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    // This function executes when the player starts the game
    override fun onResume() {
        super.onResume()

        // Tell the gameView resume method to execute
        spaceShooterView?.resume()
    }

    // This method executes when the player quits the game
    override fun onPause() {
        super.onPause()

        // Tell the gameView pause method to execute
        spaceShooterView?.pause()
    }
}