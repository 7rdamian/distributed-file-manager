package com.example.fileexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import com.example.fileexplorer.data.AuthManager
import com.example.fileexplorer.ui.FileExplorerScreen
import com.example.fileexplorer.ui.LoginScreen
import com.example.fileexplorer.ui.theme.FileExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FileExplorerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showMainApp by remember { mutableStateOf(AuthManager.isUserLoggedIn()) }

                    if (showMainApp) {
                        FileExplorerScreen()
                    } else {
                        LoginScreen(
                            onLoginSuccess = { showMainApp = true }
                        )
                    }
                }
            }
        }
    }
}