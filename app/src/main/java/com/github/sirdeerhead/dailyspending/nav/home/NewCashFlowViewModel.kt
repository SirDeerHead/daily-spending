package com.github.sirdeerhead.dailyspending.nav.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewCashFlowViewModel : ViewModel() {

    var date = MutableLiveData<String>()
    var amount = MutableLiveData<Double>()
    var category = MutableLiveData<String>()
    var description = MutableLiveData<String>()

}