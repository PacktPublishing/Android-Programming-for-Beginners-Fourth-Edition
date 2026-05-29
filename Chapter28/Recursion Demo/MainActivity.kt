package com.example.recursiondemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                Node(3)
            )
        )

        setContent {
            NodeView(node = tree, depth = 0)
        }
    }
}

@Composable
fun NodeView(node: Node, depth: Int) {
    Column(modifier = Modifier.padding(start = (depth * 16).dp)) {
        Text(text = "Node ${node.value}")
        node.children.forEach { child ->
            NodeView(child, depth + 1)
        }
    }
}