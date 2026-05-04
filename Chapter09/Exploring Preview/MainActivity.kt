package com.example.exploringpreview

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import com.example.exploringpreview.ui.theme.ExploringPreviewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { padding ->
                SimpleButton(
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun SimpleButton(modifier: Modifier = Modifier) {

    val context = LocalContext.current  // ✅ Composable context

    Column(modifier = modifier) {
        Text(text = "Button Demo",
            color = Color.Red,
            fontSize = 48.sp
        )

        Button(
            onClick = {
                Toast.makeText(context,
                    "Hello, Button",
                    Toast.LENGTH_SHORT).show()
            },

            shape = CircleShape,
            modifier = Modifier.size(128.dp)
        ) {
            Text(text = "1")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleButtonPreview() {
    SimpleButton()
}


@Composable
fun SimpleImage() {
    Image(
        painter = painterResource(id = android.R.drawable.ic_dialog_info),
        contentDescription = "Favorite",
        colorFilter = ColorFilter.tint(Color.Green)//,
        //modifier = Modifier.size(128.dp).alpha(0.25f)

    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSimpleImage() {
    SimpleImage()
}

