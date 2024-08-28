package com.example.hhrutest.util

fun humanUtil(peopleNumber : Int) : String {
    return if (peopleNumber % 10 == 1 || peopleNumber % 10 > 4 || peopleNumber in listOf(12, 13, 14)) {
        "человек"
    } else {
        "человека"
    }
}

fun humanAppliedUtil(peopleNumber: Int) : String {
    return if (peopleNumber > 1) {
        "откликнулись"
    } else {
        "откликнулся"
    }
}

fun humanLookingUtil(peopleNumber: Int) : String {
    return if (peopleNumber > 1) {
        "смотрят"
    } else {
        "смотрит"
    }
}