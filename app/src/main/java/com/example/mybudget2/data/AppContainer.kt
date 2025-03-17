package com.example.mybudget2.data

import android.content.Context


interface AppContainer {
    val historyRepository: HistoryRepository
}

class AppDateContainer(private val context: Context) : AppContainer {
    override val historyRepository: HistoryRepository by lazy {
        OfflineHistoryRepository(HistoryDatabase.getDatabase(context).historyDao())
    }
}