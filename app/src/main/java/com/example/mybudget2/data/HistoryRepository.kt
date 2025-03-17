package com.example.mybudget2.data
import kotlinx.coroutines.flow.Flow


interface HistoryRepository {
    fun getAllHistoryStream(): Flow<List<History>>

    fun getHistoryStream(id: Int): Flow<History?>

    suspend fun insertHistory(history: History)
}