package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Address (
    val town: String,
    val street: String,
    val house: String
)