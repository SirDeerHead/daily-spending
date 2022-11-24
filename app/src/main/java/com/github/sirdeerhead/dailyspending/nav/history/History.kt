package com.github.sirdeerhead.dailyspending.nav.history

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sirdeerhead.dailyspending.CashFlowViewModel
import com.github.sirdeerhead.dailyspending.MainActivity
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.FragmentHistoryBinding
import com.github.sirdeerhead.dailyspending.databinding.FragmentUpdateCashFlowBinding
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import kotlinx.coroutines.launch
import com.github.sirdeerhead.dailyspending.room.CashFlowAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class History : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var cashFlowViewModel: CashFlowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()

        cashFlowViewModel = ViewModelProvider(this)[CashFlowViewModel::class.java]

        lifecycleScope.launch{
            cashFlowDao.fetchAllCashFlows().collect{
                val list = ArrayList(it)
                setupListOfCashFlowIntoRecyclerView(list, cashFlowDao)
            }
        }

        return binding.root
    }

    private fun updateRecordDialog(id:Int, cashFlowDao: CashFlowDao){
        val updateDialog = BottomSheetDialog(requireContext(), com.google.android.material.R.style.Theme_Design_BottomSheetDialog)
        updateDialog.setCancelable(false)
        val binding = FragmentUpdateCashFlowBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch{
            cashFlowDao.fetchCashFlow(id).collect{
                if(it != null){
                    binding.updateDate.setText(it.date)
                    binding.updateAmount.setText(it.amount.toString())
                    binding.updateDropdownCategory.setText(it.category)
                    binding.updateDescription.setText(it.description)
                }
            }
        }

        binding.updateCashFlow.setOnClickListener{
            val date = binding.updateDate.text.toString()
            val amount = binding.updateAmount.text.toString()
            val category = binding.updateDropdownCategory.text.toString()
            val description = binding.updateDescription.text.toString()

            if(date.isNotEmpty() && amount.isNotEmpty()
                && category.isNotEmpty() && description.isNotEmpty()){
                lifecycleScope.launch{
                    cashFlowDao.update(CashFlowEntity(id,date,amount.toDouble(),category,description))
                    Toast.makeText(
                        activity?.applicationContext,
                        "Cash Flow Updated.",
                        Toast.LENGTH_LONG).show()

                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    "Data inputted can't be empty.",
                    Toast.LENGTH_LONG).show()
            }

        }

        binding.cancelCashFlow.setOnClickListener {
            updateDialog.dismiss()
        }

        binding.deleteCashFlow.setOnClickListener {
            deleteRecordAlertDialog(id, cashFlowDao)
            updateDialog.dismiss()
        }

        updateDialog.show()
    }

    private fun deleteRecordAlertDialog(id:Int, cashFlowDao: CashFlowDao){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Cash Flow")
        builder.setIcon(R.drawable.ic_delete)

        builder.setPositiveButton("Yes"){dialogInterface, _ ->
            lifecycleScope.launch{
                cashFlowDao.delete(CashFlowEntity(id))

                Toast.makeText(
                    activity?.applicationContext,
                    "Cash Flow deleted successfully.",
                    Toast.LENGTH_LONG).show()
            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun setupListOfCashFlowIntoRecyclerView(cashFlowList: ArrayList<CashFlowEntity>,
                                                    cashFlowDao: CashFlowDao){
        if(cashFlowList.isNotEmpty()){
            val itemAdapter = CashFlowAdapter(cashFlowList){ updateId ->
                updateRecordDialog(updateId, cashFlowDao)
            }
            binding.rvAllHistory.layoutManager = LinearLayoutManager(activity)
            binding.rvAllHistory.adapter = itemAdapter
        } else {
            Toast.makeText(activity,
                "No Cash Flow found. Please add if you have one.",
                Toast.LENGTH_LONG).show()
        }
    }
}