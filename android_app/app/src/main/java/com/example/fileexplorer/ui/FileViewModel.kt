package com.example.fileexplorer.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fileexplorer.data.RetrofitClient
import com.example.fileexplorer.model.FileItem
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File

class FileViewModel : ViewModel() {
    val files = mutableStateListOf<FileItem>()

    var currentEditingFile by mutableStateOf<String?>(null)
    var fileContent by mutableStateOf("")
    var isSaving by mutableStateOf(false)

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

    fun openFile(fileName: String) {
        currentEditingFile = fileName
        fileContent = "Loading..."
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getFileContent(fileName)
                fileContent = response.string()

            } catch (e: Exception) {
                e.printStackTrace()
                fileContent = "Error loading file: ${e.message}"
            }
        }
    }

    fun closeEditor() {
        currentEditingFile = null
        fileContent = ""
    }

    fun saveChanges() {
        val fileName = currentEditingFile ?: return
        isSaving = true
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.saveFileContent(fileName, fileContent)
                isSaving = false
                closeEditor()
                fetchFiles()
            } catch (e: Exception) {
                isSaving = false
                e.printStackTrace()
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
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteFile(file: FileItem) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deleteFile(file.name)
                fetchFiles()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun openExternalFile(context: Context, fileName: String) {
        viewModelScope.launch {
            try {
                val responseBody = RetrofitClient.apiService.downloadFile(fileName)
                val tempFile = File(context.cacheDir, fileName)

                responseBody.byteStream().use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                val contentUri: Uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    tempFile
                )

                val extension = MimeTypeMap.getFileExtensionFromUrl(contentUri.toString())
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(contentUri, mimeType)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(Intent.createChooser(intent, "Open file with..."))
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}