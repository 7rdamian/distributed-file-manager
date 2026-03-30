package com.example.fileexplorer.data

import com.example.fileexplorer.model.FileItem
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Streaming

interface ApiService {
    @GET("files")
    suspend fun getFiles(): List<String>

    @POST("files/create")
    suspend fun createFile(@Body file: FileItem)

    @DELETE("files/{name}")
    suspend fun deleteFile(@Path("name") fileName: String)

    @GET("files/content/{name}")
    suspend fun getFileContent(@Path("name") fileName: String): ResponseBody
    @POST("files/save/{name}")
    suspend fun saveFileContent(@Path("name") fileName: String, @Body content: String): String

    @Streaming
    @GET("files/download/{name}")
    suspend fun downloadFile(@Path("name") fileName: String): ResponseBody
}