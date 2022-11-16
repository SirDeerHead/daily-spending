package com.github.sirdeerhead.dailyspending.room

import android.app.Application

class CashFlowApp: Application() {
    val database by lazy{
        CashFlowDatabase.getInstance(this)
    }
}