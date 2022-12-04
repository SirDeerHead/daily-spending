package com.github.sirdeerhead.dailyspending.nav.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sirdeerhead.dailyspending.CashFlowViewModel
import com.github.sirdeerhead.dailyspending.databinding.FragmentHomeBinding
import com.github.sirdeerhead.dailyspending.room.*
import kotlinx.coroutines.launch

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var cashFlowViewModel: CashFlowViewModel
    private lateinit var mainAdapter: CashFlowAdapterHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        cashFlowViewModel = ViewModelProvider(this)[CashFlowViewModel::class.java]
        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()

        binding.btnCashFlowButton.setOnClickListener {
            NewCashFlow().show(childFragmentManager,"newCashFlowTag")
        }

        lifecycleScope.launch{
            cashFlowDao.fetchCashFlowsDESC().collect{
                val list = ArrayList(it)
                setItemAdapter(list, cashFlowDao)
                setupListOfCashFlowIntoRecyclerView(list)
            }
        }

        return binding.root
    }

    private fun setupListOfCashFlowIntoRecyclerView(cashFlowList: ArrayList<CashFlowEntity>){
        if(cashFlowList.isNotEmpty()){
            binding.rvLastAddedCashFlow.layoutManager = LinearLayoutManager(activity)
            binding.rvLastAddedCashFlow.adapter = mainAdapter
        }
    }

    private fun setItemAdapter(cashFlowList: ArrayList<CashFlowEntity>,
                               cashFlowDao: CashFlowDao){
        val itemAdapter = CashFlowAdapterHome(cashFlowList)
        mainAdapter = itemAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}