package com.github.sirdeerhead.dailyspending.room

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CashFlowApp: Application() {
    val database by lazy{
        CashFlowDatabase.getInstance(this)
    }
}