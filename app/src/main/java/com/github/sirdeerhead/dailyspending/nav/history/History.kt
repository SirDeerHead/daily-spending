package com.github.sirdeerhead.dailyspending.nav.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sirdeerhead.dailyspending.databinding.FragmentHistoryBinding
import com.github.sirdeerhead.dailyspending.room.CashFlowAdapter
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import kotlinx.coroutines.launch

class History : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()

        lifecycleScope.launch{
            cashFlowDao.fetchAllCashFlows().collect{
                val list = ArrayList(it)

                setupListOfCashFlowIntoRecyclerView(list, cashFlowDao)
            }
        }

        return binding.root
    }

    private fun setupListOfCashFlowIntoRecyclerView(cashFlowList: ArrayList<CashFlowEntity>,
                                                    cashFlowDao: CashFlowDao){
        if(cashFlowList.isNotEmpty()){
            val itemAdapter = CashFlowAdapter(cashFlowList)
            binding.rvAllHistory.layoutManager = LinearLayoutManager(activity)
            binding.rvAllHistory.adapter = itemAdapter
        } else {
            Toast.makeText(activity,
                "No Cash Flow found. Please add if you have one.",
                Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}