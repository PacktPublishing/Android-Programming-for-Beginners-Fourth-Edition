package com.example.dashboard

/*
================================================================================
Top and Bottom App Bars in Jetpack Compose
================================================================================

GENERAL OVERVIEW:
-----------------
1. **TopAppBar** and **BottomAppBar** are part of Material Design components in
   Jetpack Compose. They provide persistent navigation and action surfaces
   at the top and bottom of the screen.

2. **TopAppBar** (also called Toolbar in classic Android Views) typically:
   - Displays the screen title.
   - Provides navigation (back/up buttons, hamburger menu for drawer).
   - Hosts action icons (search, settings, etc.).
   - Can also display custom composables like TabRow.

3. **BottomAppBar**:
   - Hosts navigation or action items at the bottom of the screen.
   - Can include FAB (Floating Action Button) integration.
   - Often used for primary actions, persistent navigation, or contextual actions.

WHY THEY ARE IMPORTANT:
-----------------------
- **Consistency:** Users expect a predictable place for navigation and actions.
- **Usability:** Immediate access to actions like search, create, or save.
- **Material Design compliance:** Standardized app layout improves user experience.
- **Integration with Scaffold:** They work seamlessly with Compose's `Scaffold` to
  handle layout padding, screen insets, and adaptive design.

CODE BREAKDOWN:
----------------
Scaffold(
    topBar = { ... },
    bottomBar = { ... }
) { innerPadding -> ... }

1. **Scaffold**:
   - Provides a basic layout structure with slots for topBar, bottomBar,
     floatingActionButton, drawer, snackbar, etc.
   - Automatically handles padding so content isn't obscured by bars.
   - The lambda inside Scaffold receives `innerPadding` to apply proper padding
     to the main content.

2. **TopAppBar**(
       title = { Text("Dashboard Top Bar") }
   )
   - **title:** A composable to display the title. Can be `Text`, `Row`, or
     any custom layout.
   - Other optional parameters (not used here) include:
     - **navigationIcon:** A composable for navigation icon (back, menu).
     - **actions:** Composable row for action icons/buttons.
     - **backgroundColor:** The background color of the bar.
     - **contentColor:** Tint color for icons/text.
     - **elevation:** Shadow depth under the app bar.
   - In your code, it simply displays the title "Dashboard Top Bar".

3. **BottomAppBar** { ... }
   - BottomAppBar is a composable that hosts a horizontal row of content.
   - Inside it, you can place icons, text, or custom composables.
   - Optional parameters include:
     - **backgroundColor, contentColor:** Similar to TopAppBar.
     - **cutoutShape:** Shape to accommodate a FAB if present.
     - **elevation:** Shadow depth under the bar.
   - In your code, you placed a `Text` composable with a `Modifier.padding()`.
     - `Modifier.padding()` without parameters defaults to 0, effectively no padding.

4. **Text(modifier = Modifier.padding(innerPadding), ...)**
   - The main content inside Scaffold uses `innerPadding` to prevent content
     from being overlapped by top or bottom bars.
   - This is essential for proper layout and avoids clipping UI elements.

KEY TAKEAWAYS:
---------------
- TopAppBar and BottomAppBar provide consistent UI patterns for navigation
  and actions.
- They integrate with Scaffold to manage layout padding and screen insets.
- TopAppBar is usually for screen identity, navigation, and actions.
- BottomAppBar is for primary/secondary actions or persistent navigation.
- Parameters like backgroundColor, contentColor, elevation, and content slots
  allow full customization.

USAGE TIP:
----------
- For dynamic content or actions, you can replace static Text with Row of Icons
  or Buttons.
- You can also combine BottomAppBar with FloatingActionButton to create
  Material Design “cutout” FAB layouts.
- Always use innerPadding to avoid content being obscured by app bars.
================================================================================
*/

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Dashboard Top Bar") }
                    )
                },
                bottomBar = {
                    BottomAppBar {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Dashboard Bottom Bar"
                        )
                    }
                }
            ) { innerPadding ->
                // State to hold the slider value
                var sliderValue by remember { mutableFloatStateOf(0f) }
                // State to hold the player health value
                val playerHealth by remember { mutableFloatStateOf(0.7f) }
                // State to hold the star rating
                var rating by remember { mutableIntStateOf(0) }

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Text(text = "Current Slider Value: ${sliderValue.toInt()}")
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        valueRange = 0f..10f
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = "Player Health: ${(playerHealth * 100).toInt()}%")
                    LinearProgressIndicator(
                        progress = { playerHealth },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(text = "Rating: $rating / 5")
                    Row {
                        for (i in 1..5) {
                            IconButton(onClick = { rating = i }) {
                                Icon(
                                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                                    contentDescription = "Star $i",
                                    tint = if (i <= rating) Color(0xFFFFD700) else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
