package com.example.data.services

import io.ktor.client.HttpClient
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.http.HttpMethod
import io.ktor.util.pipeline.StackWalkingFailedFrame.context


interface VacancyService {
    suspend fun getAll(): HttpResponse
}

class VacancyServiceImpl (private val client: HttpClient) : VacancyService {

    override suspend fun getAll(): HttpResponse {
        val outputFile = File(context.cacheDir, "yourFileName")
        client.get {
            url("https://drive.google.com/uc?export=download&id=1pPHmvMqAX7MSun2xkJV4znBzLx1MfofffoZftTFLnzI")
            method = HttpMethod.Get
        }.bodyAsChannel()
    }
}