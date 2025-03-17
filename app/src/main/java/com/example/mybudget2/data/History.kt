package com.example.mybudget2.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val data: String,
    val amount: Float,
    val pattern: String
)
