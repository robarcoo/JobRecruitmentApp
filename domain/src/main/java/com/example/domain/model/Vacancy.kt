package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Vacancy (
    val id: String,
    val lookingNumber: Long? = null,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Long? = null,
    val description: String? = null,
    val responsibilities: String,
    val questions: List<String>
)