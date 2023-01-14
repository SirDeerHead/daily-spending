package com.github.sirdeerhead.dailyspending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import com.github.sirdeerhead.dailyspending.room.Repository

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(), LifecycleObserver {

    val fetchAllCashFlows = repository.fetchAllCashFlows().asLiveData()

    fun searchRoom(searchQuery: String): LiveData<List<CashFlowEntity>>{
        return repository.searchRoom(searchQuery).asLiveData()
    }
}