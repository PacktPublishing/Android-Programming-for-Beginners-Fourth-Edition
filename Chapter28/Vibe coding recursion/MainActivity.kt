package com.example.recursiondemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Node(val value: Int, val children: List<Node> = emptyList())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tree = Node(
            1,
            listOf(
                Node(2, listOf(Node(4), Node(5))),
                Node(3, listOf(Node(6)))
            )
        )

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.padding(16.dp)) {
                    NodeView(node = tree, depth = 0)
                }
            }
        }
    }
}

@Composable
fun NodeView(node: Node, depth: Int) {
    // State to track if this node is expanded
    var expanded by remember { mutableStateOf(false) }
    val hasChildren = node.children.isNotEmpty()

    Column(modifier = Modifier.padding(start = if (depth > 0) 16.dp else 0.dp)) {
        // Node title - clickable if it has children
        Text(
            text = (if (hasChildren) (if (expanded) "▼ " else "▶ ") else "• ") + "Node ${node.value}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .clickable(enabled = hasChildren) { expanded = !expanded }
                .padding(vertical = 4.dp)
        )

        // Animate the expansion and show children only when expanded
        AnimatedVisibility(visible = expanded) {
            Column {
                node.children.forEach { child ->
                    NodeView(child, depth + 1)
                }
            }
        }
    }
}
