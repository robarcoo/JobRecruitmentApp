package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Experience (
    val previewText: String,
    val text: String
)
