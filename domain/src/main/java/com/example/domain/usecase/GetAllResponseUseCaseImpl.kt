package com.example.domain.usecase

import com.example.domain.model.DownloadResult
import com.example.domain.repository.ResponseRepository
import kotlinx.coroutines.flow.Flow

class GetAllResponseUseCaseImpl(private val repository: ResponseRepository) : GetAllResponseUseCase {
    override fun execute(): Flow<DownloadResult> {
        return repository.getAll()
    }
}