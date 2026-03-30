package com.example.fileexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileEditorScreen(viewModel: FileViewModel) {
    val fileName = viewModel.currentEditingFile ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editing: $fileName") },
                actions = {
                    Button(
                        onClick = { viewModel.saveChanges() },
                        enabled = !viewModel.isSaving
                    ) {
                        if (viewModel.isSaving) Text("Saving...") else Text("Save")
                    }
                    TextButton(onClick = { viewModel.closeEditor() }) {
                        Text("Cancel", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { padding ->
        TextField(
            value = viewModel.fileContent,
            onValueChange = { viewModel.fileContent = it },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            placeholder = { Text("Type something...") }
        )
    }
}