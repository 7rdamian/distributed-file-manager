package com.example.fileexplorer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddFileDialog(
    onDismiss: () -> Unit,
    onCreate: (String, Boolean) -> Unit
) {

    var fileName by remember { mutableStateOf("") }
    var isFolder by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New") },
        text = {
            Column(modifier = Modifier.padding(top = 8.dp)) {

                // The text box where the user types
                OutlinedTextField(
                    value = fileName,
                    onValueChange = { fileName = it },
                    label = { Text("Name (e.g., photos)") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // A Row to put the Checkbox and Text side-by-side
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isFolder,
                        onCheckedChange = { isFolder = it }
                    )
                    Text("Is this a folder?")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fileName.isNotBlank()) {
                        onCreate(fileName, isFolder)
                    }
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}