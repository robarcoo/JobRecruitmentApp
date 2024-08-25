package com.example.data.repository

import android.content.Context
import com.example.data.services.ResponseService
import com.example.domain.model.DownloadResult
import com.example.domain.model.Response
import com.example.domain.repository.ResponseRepository
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.isSuccess
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ResponseRepositoryImpl(private val remoteDataSource : ResponseService,
                             private val context : Context) : ResponseRepository {

    override fun getAll(): Flow<DownloadResult> {
        return flow {
            val outputFile = java.io.File(context.cacheDir, "мок json.json")
            outputFile.createNewFile()
            val response = remoteDataSource.getAll()
            try {
                if (response.status.isSuccess()) {
                    response.bodyAsChannel().copyAndClose(outputFile.writeChannel())
                    val jsonString = outputFile.readText()
                    val result = Gson().fromJson(jsonString, Response::class.java)
                    emit(DownloadResult.Success(result))
                } else {
                    emit(DownloadResult.Error(response.toString()))
                }

            } catch (e: Exception) {
                DownloadResult.Error("Error")
            } finally {
                outputFile.delete()
            }
        }.flowOn(Dispatchers.IO)

    }
}