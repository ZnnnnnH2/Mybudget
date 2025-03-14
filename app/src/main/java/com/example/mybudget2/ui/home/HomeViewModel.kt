package com.example.mybudget2.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val BALANCE = arrayOf(1200F, 30F, 100F, 200F, 300F)
    private val item = arrayOf("food", "transport", "entertainment", "necessity", "others")
    private val sharedPre = application.getSharedPreferences("myBank", Context.MODE_PRIVATE)

    private val _balance = MutableLiveData<Float>().apply {
        value = sharedPre.getFloat("balance", 0F)
    }
    val balance: LiveData<Float> = _balance
    fun updateBalance(flee: Float) {
        _balance.value = _balance.value?.plus(flee)
        sharedPre.edit().apply {
            putFloat("balance", _balance.value!!)
            apply()
        }
        for(i in 0..4){
            sharedPre.edit().apply{
                putFloat(item[i], BALANCE[i])
                apply()
            }
        }
    }
    fun initBalance() {
        _balance.value = 0F
        sharedPre.edit().apply {
            putFloat("balance", 0F)
            apply()
        }
    }

    private val _spare = MutableLiveData<Float>().apply {
        value = 0F
        for(i in 0..4) {
            value = value?.plus(sharedPre.getFloat(item[i], BALANCE[i]))
        }
    }
    val spare: LiveData<Float> = _spare
    fun updateSpare() {
        _spare.value = 0F
        for(i in 0..4){
            _spare.value = _spare.value?.plus(sharedPre.getFloat(item[i], BALANCE[i]))
        }
    }
    fun initSpare() {
        _spare.value = 0F
        for(i in 0..4){
            sharedPre.edit().apply {
                putFloat(item[i], BALANCE[i])
                apply()
            }
            _spare.value = _spare.value!! + BALANCE[i]
        }
    }
}