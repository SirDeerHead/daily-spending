package com.github.sirdeerhead.dailyspending.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val cashFlowDao: CashFlowDao
) {

    fun fetchAllCashFlows(): Flow<List<CashFlowEntity>> {
        return cashFlowDao.fetchAllCashFlows()
    }

    fun searchRoom(searchQuery: String): Flow<List<CashFlowEntity>>{
        return cashFlowDao.searchRoom(searchQuery)
    }

}