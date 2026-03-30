package com.example.fileexplorer.model

data class FileItem(
    val name: String,
    val isDirectory: Boolean,
    val size: String,
    val path: String
)