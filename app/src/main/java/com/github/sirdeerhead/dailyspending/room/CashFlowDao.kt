package com.github.sirdeerhead.dailyspending.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CashFlowDao {

    @Insert
    suspend fun insert(cashFlowEntity: CashFlowEntity)

    @Update
    suspend fun update(cashFlowEntity: CashFlowEntity)

    @Delete
    suspend fun delete(cashFlowEntity: CashFlowEntity)

    @Query("SELECT * FROM `cashFlow-table`")
    fun fetchAllCashFlows():Flow<List<CashFlowEntity>>

    @Query("SELECT * FROM `cashFlow-table` WHERE id=:id")
    fun fetchCashFlow(id:Int):Flow<CashFlowEntity>
}