package com.example.domain.usecase

import com.example.domain.model.DownloadResult
import kotlinx.coroutines.flow.Flow

interface GetAllResponseUseCase {
    fun execute() : Flow<DownloadResult>
}