package com.example.domain.model

sealed class DownloadResult {

    data class Success<T>(val value: T) : DownloadResult()

    data class Error(val message: String, val cause: Exception? = null) : DownloadResult()

    data class Progress(val value : Unit): DownloadResult()
}