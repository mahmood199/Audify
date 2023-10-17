package com.example.scrutinizing_the_service.v2.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)