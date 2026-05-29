@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.c21

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditContactScreen(viewModel: ContactViewModel, navController: NavController, contactId: Long?) {
    val existing = viewModel.contacts.find { it.id == contactId }
    var name by remember { mutableStateOf(existing?.name ?: "") }
    var address by remember { mutableStateOf(existing?.address ?: "") }
    var jobTitle by remember { mutableStateOf(existing?.jobTitle ?: "") }

// All the rest of the code in this section goes here
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (existing == null)
                            "Add Contact"
                        else
                            "Edit Contact"
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text("Name")
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            OutlinedTextField(
                value = jobTitle,
                onValueChange = {
                    jobTitle = it
                },
                label = {
                    Text("Job Title")
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                },
                label = {
                    Text("Address")
                }
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Button(
                onClick = {
                    if (existing != null) {
                        viewModel.updateContact(
                            existing.copy(
                                name = name,
                                address = address,
                                jobTitle = jobTitle
                            )
                        )
                    } else {
                        viewModel.addContact(
                            name,
                            address,
                            jobTitle
                        )
                    }
                    navController.navigateUp()
                }
            ) {
                Text("Save")
            }
        }
    }
}// End of EditContactScreen