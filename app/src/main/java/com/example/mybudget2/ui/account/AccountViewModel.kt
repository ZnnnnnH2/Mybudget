package com.example.mybudget2.ui.account

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val balance = arrayOf(1200F, 30F, 100F, 200F, 300F)
    private val sharedPre = application.getSharedPreferences("myBank", Context.MODE_PRIVATE)
    private val item = arrayOf("food", "transport", "entertainment", "necessity", "others")

    private val _food = MutableLiveData<Float>().apply {
        value = sharedPre.getFloat("food", balance[0])
    }
    val food: LiveData<Float> = _food
    fun updateFoodAccountBalance(flee: Float) {
        _food.value = _food.value?.minus(flee)
        sharedPre.edit().apply {
            putFloat("food", _food.value!!)
            apply()
        }
    }

    private val _transport = MutableLiveData<Float>().apply {
        value = sharedPre.getFloat("transport", balance[1])
    }
    val transport: LiveData<Float> = _transport
    fun updateTransportAccountBalance(flee: Float) {
        _transport.value = _transport.value?.minus(flee)
        sharedPre.edit().apply {
            putFloat("transport", _transport.value!!)
            apply()
        }
    }

    private val _entertainment = MutableLiveData<Float>().apply {
        value = sharedPre.getFloat("entertainment", balance[2])
    }
    val entertainment: LiveData<Float> = _entertainment
    fun updateEntertainmentAccountBalance(flee: Float) {
        _entertainment.value = _entertainment.value?.minus(flee)
        sharedPre.edit().apply {
            putFloat("entertainment", _entertainment.value!!)
            apply()
        }
    }

    private val _necessities = MutableLiveData<Float>().apply {
        value = sharedPre.getFloat("necessity", balance[3])
    }
    val necessities: LiveData<Float> = _necessities
    fun updateNecessitiesAccountBalance(flee: Float) {
        _necessities.value = _necessities.value?.minus(flee)
        sharedPre.edit().apply {
            putFloat("necessity", _necessities.value!!)
            apply()
        }
    }

    private val _others = MutableLiveData<Float>().apply {
        value = sharedPre.getFloat("others", balance[4])
    }
    val others: LiveData<Float> = _others
    fun updateOthersAccountBalance(flee: Float) {
        _others.value = _others.value?.minus(flee)
        sharedPre.edit().apply {
            putFloat("others", _others.value!!)
            apply()
        }
    }

    fun update(){
        _food.value = sharedPre.getFloat("food", balance[0])
        _transport.value = sharedPre.getFloat("transport", balance[1])
        _entertainment.value = sharedPre.getFloat("entertainment", balance[2])
        _necessities.value = sharedPre.getFloat("necessity", balance[3])
        _others.value = sharedPre.getFloat("others", balance[4])
    }
}