package com.example.data.services

import android.content.Context
import com.example.domain.model.DownloadResult
import com.example.domain.model.Response
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File


interface ResponseService {
    suspend fun getAll(): HttpResponse
}

class ResponseServiceImpl (private val client: HttpClient) : ResponseService {

    override suspend fun getAll(): HttpResponse {
        return client.get {
            url("https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&amp;export=download")
            method = HttpMethod.Get
        }

    }
}


