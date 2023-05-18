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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState


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
                    MainScreen()
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val items = listOf(
            AppScreens.Dialing,
            AppScreens.CallHistory,
            AppScreens.Contacts,
            AppScreens.Messages,
            AppScreens.Settings
        )

        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = { /* Add your icon here */ },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.title,
                            onClick = {
                                navController.navigate(screen.title) {
                                    navController.graph.startDestinationRoute?.let { popUpTo(it) }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = AppScreens.Dialing.title) {
                    composable(AppScreens.Dialing.title) { DialingScreen() }
                    composable(AppScreens.CallHistory.title) { CallHistoryScreen() }
                    composable(AppScreens.Contacts.title) { ContactsListScreen(viewModel) }
                    composable(AppScreens.Messages.title) { MessagesScreen() }
                    composable(AppScreens.Settings.title) { SettingsScreen() }
                }
            }
        }
    }
}