package com.github.sirdeerhead.dailyspending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CashFlowViewModel : ViewModel() {

    var date = MutableLiveData<String>()
    var amount = MutableLiveData<Double>()
    var category = MutableLiveData<String>()
    var description = MutableLiveData<String>()

}