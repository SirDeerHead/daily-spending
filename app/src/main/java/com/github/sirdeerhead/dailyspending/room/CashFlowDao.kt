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

    @Query("SELECT * FROM `cashFlow-table` ORDER BY date DESC")
    fun fetchAllCashFlows():Flow<List<CashFlowEntity>>

    @Query("SELECT * FROM `cashFlow-table` ORDER BY id DESC")
    fun fetchCashFlowsDESC():Flow<List<CashFlowEntity>>

    @Query("SELECT * FROM `cashFlow-table` WHERE id=:id")
    fun fetchCashFlow(id:Int):Flow<CashFlowEntity>

    @Query("SELECT SUM(amount) FROM `cashflow-table` WHERE amount >= 0")
    suspend fun getTotalIncome():Double

    @Query("SELECT SUM(amount) FROM `cashflow-table` WHERE amount <= 0")
    suspend fun getTotalExpense():Double

    @Query("SELECT * FROM `cashFlow-table` WHERE date LIKE :searchQuery " +
                                            "OR amount LIKE :searchQuery " +
                                            "OR category LIKE :searchQuery " +
                                            "OR description LIKE :searchQuery")
    fun searchRoom(searchQuery: String): Flow<List<CashFlowEntity>>
}