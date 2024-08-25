package com.example.data.services

import android.content.Context
import com.example.data.client.httpClientAndroid
import com.example.domain.model.DownloadResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


interface VacancyService {
    suspend fun getAll(): Flow<DownloadResult>
}

class VacancyServiceImpl (private val client: HttpClient, private val context : Context) : VacancyService {

    override suspend fun getAll(): Flow<DownloadResult> {
        return flow {
            val outputFile = File(context.cacheDir, "мок json.json")
            val isNewFileCreated: Boolean = outputFile.createNewFile()
            val response = client.get {
                url("https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&amp;export=download")
                method = HttpMethod.Get
            }
            val lineList = mutableListOf<String>()
            try {
                if (response.status.isSuccess()) {
                    response.bodyAsChannel().copyAndClose(outputFile.writeChannel())
                    outputFile.useLines { lines -> lines.forEach { lineList.add(it) }}
                    emit(DownloadResult.Success(lineList))
                } else {
                    emit(DownloadResult.Error(response.toString()))
                }
            } catch (e: Exception) {
                DownloadResult.Error("Error")
            }



        }.flowOn(Dispatchers.IO)

    }
}


