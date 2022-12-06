package com.github.sirdeerhead.dailyspending.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.github.sirdeerhead.dailyspending.databinding.FragmentStatsBinding
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Stats : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()

        /*
        binding.floatingActionButton.setOnClickListener{
            addItem(cashFlowDao)
        }
        */

        setPieChart()

        return binding.root
    }

    // TODO: https://www.youtube.com/watch?v=2ymbQpreCNM&ab_channel=VishalKamboj

    fun setPieChart() {

    }

    private fun addItem(cashFlowDao: CashFlowDao) {
        lifecycleScope.launch {
            cashFlowDao.insert(
                CashFlowEntity(
                    date = "12.12.2022", amount = 20.1,
                    category = "Jedzenie", description = "Record 1/3"
                )
            )
            cashFlowDao.insert(
                CashFlowEntity(
                    date = "12.12.2022", amount = -20.1,
                    category = "Jedzenie", description = "Record 2/3"
                )
            )
            cashFlowDao.insert(
                CashFlowEntity(
                    date = "12.12.2022", amount = 0.0,
                    category = "Jedzenie", description = "Record 3/3"
                )
            )
            cashFlowDao.insert(
                CashFlowEntity(
                    date = "03.11.2020", amount = 123.0,
                    category = "Ubrania", description = "Record -1/2"
                )
            )
            cashFlowDao.insert(
                CashFlowEntity(
                    date = "04.10.2021", amount = -9990.0,
                    category = "Relaks", description = "Record -2/2"
                )
            )
        }

        Toast.makeText(activity, "Cash-s Flow added", Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}