package com.example.mybudget2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

    @Delete
    suspend fun delete(history: History)

    @Query("SELECT * from history WHERE id = :id")
    fun getHistory(id: Int): Flow<History>

    @Query("SELECT * from history ORDER BY data")
    fun getAllItems(): Flow<List<History>>
}