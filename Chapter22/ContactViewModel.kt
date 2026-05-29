package com.example.celebbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.mutableStateListOf

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactDao = CelebBookDatabase.getDatabase(application).contactDao()

    val contacts = mutableStateListOf<Contact>()

    init {
        viewModelScope.launch {
            loadContacts()
        }
    }

    private suspend fun loadContacts() {
        val list = withContext(Dispatchers.IO) { contactDao.getAllContacts() }
        contacts.addAll(list)
    }

    fun addContact(name: String, address: String, jobTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = Contact(name = name, address = address, jobTitle = jobTitle)
            contactDao.insertContact(contact)
            val updatedList = contactDao.getAllContacts()
            withContext(Dispatchers.Main) {
                contacts.clear()
                contacts.addAll(updatedList)
            }
        }
    }

    fun updateContact(updated: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.updateContact(updated)
            val updatedList = contactDao.getAllContacts()
            withContext(Dispatchers.Main) {
                contacts.clear()
                contacts.addAll(updatedList)
            }
        }
    }
}
