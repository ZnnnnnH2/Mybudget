package com.example.mybudget2.ui.history

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.mybudget2.data.AppDateContainer
import com.example.mybudget2.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val historyRepo = AppDateContainer(application as Context).historyRepository
    val allHistory: LiveData<List<History>> = historyRepo.getAllHistoryStream().asLiveData()

}