package com.example.fileexplorer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fileexplorer.model.FileItem

@Composable
fun FileRow(file: FileItem,
            onDeleteClick: () -> Unit,
            onFileClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1E1E1E)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onFileClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (file.isDirectory) Icons.Default.Folder else Icons.Default.Description,
                contentDescription = "File Icon",
                tint = if (file.isDirectory) Color(0xFFBB86FC) else Color.LightGray,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = file.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = if (file.isDirectory) "Folder" else file.size,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete File",
                    tint = Color(0xFFCF6679) // A soft red color
                )
            }
        }
    }
}