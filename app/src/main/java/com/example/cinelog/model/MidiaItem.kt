package com.example.cinelog.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_midia")
data class MidiaItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val type: String, // "Filme" ou "Série"
    val rating: Float,
    val review: String
)