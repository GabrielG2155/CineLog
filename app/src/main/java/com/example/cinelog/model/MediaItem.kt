package com.example.cinelog.model

data class MediaItem(
    val id: Long = 0,
    val title: String,
    val type: String, // "Filme" ou "Série"
    val rating: Float,
    val review: String
)