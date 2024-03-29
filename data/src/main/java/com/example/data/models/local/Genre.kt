package com.example.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val userSelected: Boolean = false
)