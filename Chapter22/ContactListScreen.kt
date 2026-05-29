@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.celebbook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContactListScreen(viewModel: ContactViewModel,
                      navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Celeb Book") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("edit/0") }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(viewModel.contacts.size) { index ->
                val contact = viewModel.contacts[index]
                ContactRow(contact) {
                    navController.navigate("edit/${contact.id}")
                }
            }
        }
    }
}



@Composable
fun ContactRow(contact: Contact, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(contact.name, style =
            MaterialTheme.typography.titleMedium)
        Text(contact.jobTitle, style =
            MaterialTheme.typography.bodyMedium)
        Text(contact.address, style =
            MaterialTheme.typography.bodySmall)
    }
}
