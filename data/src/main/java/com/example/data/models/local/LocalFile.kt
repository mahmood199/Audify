package com.example.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalFile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val progress: Int,
    val name: String,
    val desc: String,
    @ColumnInfo(name = "time_stamp")
    val timeStamp : Long
)