package com.github.sirdeerhead.dailyspending.room

import androidx.room.*
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

    @Query("SELECT category as expensesCategory, COUNT(category) as totalExpensesCount FROM `cashflow-table` WHERE amount < 0 GROUP BY category")
    suspend fun countedExpensesCategory(): List<CountedExpensesCategory>

        data class CountedExpensesCategory(
            var expensesCategory: String = "",

            @ColumnInfo(name = "totalExpensesCount")
            var totalExpensesCount: Float = 0.0f
        )

    @Query("SELECT category as incomesCategory, COUNT(category) as totalIncomesCount FROM `cashflow-table` WHERE amount > 0 GROUP BY category")
    suspend fun countedIncomesCategory(): List<CountedIncomesCategory>

        data class CountedIncomesCategory(
            var incomesCategory: String = "",

            @ColumnInfo(name = "totalIncomesCount")
            var totalIncomesCount: Float = 0.0f
        )
}