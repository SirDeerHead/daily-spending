package com.github.sirdeerhead.dailyspending.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val cashFlowDao: CashFlowDao
) {

    suspend fun insert(cashFlowEntity: CashFlowEntity){
        cashFlowDao.insert(cashFlowEntity)
    }


    fun fetchAllCashFlows(): Flow<List<CashFlowEntity>> {
        return cashFlowDao.fetchAllCashFlows()
    }

    fun fetchCashFlow(id:Int):Flow<CashFlowEntity>{
        return cashFlowDao.fetchCashFlow(id)
    }

    fun searchRoom(searchQuery: String): Flow<List<CashFlowEntity>>{
        return cashFlowDao.searchRoom(searchQuery)
    }

}