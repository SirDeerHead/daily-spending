package com.github.sirdeerhead.dailyspending.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
internal class CashFlowDatabaseTest : TestCase(){

    private lateinit var db: CashFlowDatabase
    private lateinit var dao: CashFlowDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CashFlowDatabase::class.java).build()
        dao = db.cashFlowDao()
    }
    @After
    fun closeDb(){
        db.close()
    }

    // UNIT TEST

    // Unit Test
    @Test
    // Test - Inserting to database
    fun testDAOInserting() = runBlocking{
        val cashFlow = CashFlowEntity(1, "10.01.2023", 10.0, "Relax", "Lorem Ipsum")
        dao.insert(cashFlow)
        val cashFlows = dao.last10CashFlows()
        assertThat(cashFlows.contains(cashFlow)).isTrue()
    }

    @Test
    fun testDAOUpdating() = runBlocking{
        val cashFlow = CashFlowEntity(1, "10.01.2023", 10.0, "Relax", "Lorem Ipsum")
        dao.insert(cashFlow)
        var last10cashFlows = dao.last10CashFlows()
        println(last10cashFlows)

        cashFlow.amount = 15.0
        dao.update(cashFlow)
        last10cashFlows = dao.last10CashFlows()
        println(last10cashFlows)

        assertThat(last10cashFlows.contains(cashFlow)).isTrue()
    }

}