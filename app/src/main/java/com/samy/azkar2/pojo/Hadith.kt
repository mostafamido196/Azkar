package com.samy.azkar2.pojo

data class Hadith(
    val id: Int,
    val matn: String,
    val isnad: String,
    val no_repeat: Int,
    var state: Int
)
