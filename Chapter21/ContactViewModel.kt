package com.example.c21

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {
    val contacts = mutableStateListOf<Contact>()
    private var nextId = 1L

    fun addContact(name: String, address: String, jobTitle: String) {
        contacts.add(Contact(id = nextId++,
            name = name, address = address, jobTitle = jobTitle))
    }

    fun updateContact(updated: Contact) {
        val index = contacts.indexOfFirst { it.id == updated.id }

        if (index != -1) {
            contacts[index] = updated
        }

    }

}