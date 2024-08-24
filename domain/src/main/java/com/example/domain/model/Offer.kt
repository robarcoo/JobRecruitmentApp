package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Offer (
    val id: String? = null,
    val title: String,
    val link: String,
    val button: Button? = null
)