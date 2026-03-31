package com.example.fileexplorer.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(viewModel: FileViewModel = viewModel()) {

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (viewModel.currentEditingFile != null) {
        FileEditorScreen(viewModel = viewModel)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TrueNAS Files", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = Color(0xFFBB86FC)
                ) {
                    Text("+", color = Color.White, style = MaterialTheme.typography.headlineSmall)
                }
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF121212))
            ) {
                items(viewModel.files) { currentFile ->
                    FileRow(
                        file = currentFile,
                        onDeleteClick = {
                            viewModel.deleteFile(currentFile)
                        },
                        // 3. ADD THIS: This is the logic that opens the file!
                        onFileClick = {
                            if (currentFile.name.endsWith(".txt", ignoreCase = true)) {
                                viewModel.openFile(currentFile.name)
                            } else {
                                viewModel.openExternalFile(context, currentFile.name)
                            }
                        }
                    )
                }
            }

            if (showDialog) {
                AddFileDialog(
                    onDismiss = { showDialog = false },
                    onCreate = { newName, isFolder ->
                        viewModel.createFile(newName, isFolder)
                        showDialog = false
                    }
                )
            }
        }
    }
}