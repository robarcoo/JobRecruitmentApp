package com.example.hhrutest.util

fun vacancyUtil(vacancySize : Int) : String {
    return if (vacancySize == 1) {
        "вакансия"
    } else if (vacancySize % 10 in listOf(2, 3, 4) && vacancySize !in listOf(12, 13, 14)) {
        "вакансии"
    } else {
        "вакансий"
    }
}