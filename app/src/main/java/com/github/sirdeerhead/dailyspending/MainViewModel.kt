package com.github.sirdeerhead.dailyspending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import com.github.sirdeerhead.dailyspending.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel(), LifecycleObserver {

    val fetchAllCashFlows = repository.fetchAllCashFlows().asLiveData()

    fun insert(cashFlowEntity: CashFlowEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(cashFlowEntity)
        }
    }

    fun searchRoom(searchQuery: String): LiveData<List<CashFlowEntity>>{
        return repository.searchRoom(searchQuery).asLiveData()
    }
}