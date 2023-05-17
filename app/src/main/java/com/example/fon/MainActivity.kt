package com.example.fon

import ContactListItem
import ContactsViewModel
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.fon.ui.theme.FonTheme
import android.Manifest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState


class MainActivity : ComponentActivity() {
    private val viewModel: ContactsViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.fetchContacts(this)
            } else {
                // Show a message to the user if the permission is not granted
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactsListScreen()
                }
            }
        }

        val permissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            viewModel.fetchContacts(this)
        }
    }

    @Composable
    fun ContactsListScreen() {
        val contacts by viewModel.contacts.observeAsState(emptyList())

        LazyColumn {
            items(contacts) { contact ->
                ContactListItem(contact)
            }
        }
    }
}