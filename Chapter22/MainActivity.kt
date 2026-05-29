package com.example.celebbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ContactViewModel(application) // manual instantiation

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    ContactListScreen(viewModel, navController)
                }
                composable("edit/{contactId}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("contactId")?.toLongOrNull()
                    EditContactScreen(viewModel, navController, id)
                }
            }
        }
    }
}
