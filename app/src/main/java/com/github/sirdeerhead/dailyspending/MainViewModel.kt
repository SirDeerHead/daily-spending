package com.github.sirdeerhead.dailyspending

import androidx.lifecycle.*
import androidx.room.Query
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import com.github.sirdeerhead.dailyspending.room.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
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