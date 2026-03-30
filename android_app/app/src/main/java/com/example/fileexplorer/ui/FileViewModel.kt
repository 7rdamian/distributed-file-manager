package com.example.fileexplorer.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fileexplorer.data.RetrofitClient
import com.example.fileexplorer.model.FileItem
import kotlinx.coroutines.launch
class FileViewModel : ViewModel() {
    val files = mutableStateListOf<FileItem>()
    init {
        fetchFiles()
    }

    fun fetchFiles() {
        viewModelScope.launch {
            try {
                val serverFiles = RetrofitClient.apiService.getFiles()
                val mappedFileItems = serverFiles.map { fileName ->
                    FileItem(
                        name = fileName,
                        isDirectory = false,
                        size = "--",
                        path = "/$fileName"
                    )
                }

                files.clear()
                files.addAll(mappedFileItems)

            } catch (e: Exception) {
                e.printStackTrace()
                files.clear()
                files.add(FileItem("Error: Could not connect", false, "0 KB", ""))
            }
        }
    }

    fun createFile(name: String, isFolder: Boolean) {
        viewModelScope.launch {
            try {
                val newFile = FileItem(
                    name = name,
                    isDirectory = isFolder,
                    size = if(isFolder) "--" else "0 KB",
                    path = "/mnt/$name"
                )
                RetrofitClient.apiService.createFile(newFile)
                fetchFiles()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFile(file: FileItem) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deleteFile(file.name)
                fetchFiles()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}