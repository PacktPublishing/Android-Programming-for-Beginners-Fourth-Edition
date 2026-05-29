package com.example.c21

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ContactViewModel()

        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "list") {
                composable("list") {
                    ContactListScreen(viewModel, navController)
                }

                composable("edit/{contactId}") { backStackEntry ->
                    val id = backStackEntry.arguments
                        ?.getString("contactId")?.toLongOrNull()
                    EditContactScreen(viewModel, navController, id)
                }
            }
        }
    }
}
