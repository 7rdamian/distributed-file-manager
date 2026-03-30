package com.example.fileexplorer.data

import com.example.fileexplorer.model.FileItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("files")
    suspend fun getFiles(): List<String>

    @POST("files/create")
    suspend fun createFile(@Body file: FileItem)

    @DELETE("files/{name}")
    suspend fun deleteFile(@Path("name") fileName: String)
}