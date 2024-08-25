package com.example.domain.repository

import com.example.domain.model.DownloadResult
import kotlinx.coroutines.flow.Flow

interface ResponseRepository {
    fun getAll() : Flow<DownloadResult>
}