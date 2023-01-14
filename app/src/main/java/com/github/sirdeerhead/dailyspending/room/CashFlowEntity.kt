package com.github.sirdeerhead.dailyspending.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashFlow-table")
data class CashFlowEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String = "",
    var amount: Double = 0.0,
    val category: String = "",
    val description: String = "",
)