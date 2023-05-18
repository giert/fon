package com.example.fon

import ContactListItem
import ContactsViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

enum class AppScreens(val title: String) {
    Dialing("Dialing"),
    CallHistory("Call History"),
    Contacts("Contacts"),
    Messages("Messages"),
    Settings("Settings")
}

@Composable
fun DialingScreen() {
    // Add your Dialing screen UI here
}

@Composable
fun CallHistoryScreen() {
    // Add your Call History screen UI here
}

@Composable
fun ContactsListScreen(viewModel: ContactsViewModel) {
    val contacts by viewModel.contacts.observeAsState(emptyList())

    LazyColumn {
        items(contacts) { contact ->
            ContactListItem(contact)
        }
    }
}

@Composable
fun MessagesScreen() {
    // Add your Messages screen UI here
}

@Composable
fun SettingsScreen() {
    // Add your Settings screen UI here
}
