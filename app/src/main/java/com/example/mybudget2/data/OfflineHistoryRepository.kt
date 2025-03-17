package com.example.mybudget2.data
import kotlinx.coroutines.flow.Flow


class OfflineHistoryRepository(private val historyDao: HistoryDao) : HistoryRepository {
    override fun getAllHistoryStream(): Flow<List<History>> = historyDao.getAllItems()

    override fun getHistoryStream(id: Int): Flow<History?> = historyDao.getHistory(id)

    override suspend fun insertHistory(history: History) = historyDao.insert(history)
}