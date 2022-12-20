package com.github.sirdeerhead.dailyspending.nav.history

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sirdeerhead.dailyspending.CashFlowViewModel
import com.github.sirdeerhead.dailyspending.MainViewModel
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.FragmentHistoryBinding
import com.github.sirdeerhead.dailyspending.databinding.FragmentUpdateCashFlowBinding
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import kotlinx.coroutines.launch
import com.github.sirdeerhead.dailyspending.room.CashFlowAdapterHistory
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class History : Fragment(),  SearchView.OnQueryTextListener{

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var cashFlowViewModel: CashFlowViewModel
    private lateinit var mainAdapter: CashFlowAdapterHistory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        cashFlowViewModel = ViewModelProvider(this)[CashFlowViewModel::class.java]
        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()

        val searchView = binding.wSearchView


        lifecycleScope.launch{
            cashFlowDao.fetchAllCashFlows().collect{
                val list = ArrayList(it)
                setItemAdapter(list, cashFlowDao)
                setupListOfCashFlowIntoRecyclerView(list)
            }
        }

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        return binding.root
    }

    @Suppress("NAME_SHADOWING")
    private fun updateRecordDialog(id:Int, cashFlowDao: CashFlowDao){
        val updateDialog = BottomSheetDialog(requireContext(), com.google.android.material.R.style.Theme_Design_BottomSheetDialog)
        updateDialog.setCancelable(false)
        val binding = FragmentUpdateCashFlowBinding.inflate(layoutInflater)

        updateDialog.setContentView(binding.root)

        val category = resources.getStringArray(R.array.category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_category_items, category)
        binding.updateDropdownCategory.setAdapter(arrayAdapter)

        binding.updateDropdownCategory.setOnClickListener{
            binding.updateDropdownCategory.text.clear()
        }

        binding.updateDate.setOnClickListener{
                val cashFlowCalendar = Calendar.getInstance()
                val year = cashFlowCalendar.get(Calendar.YEAR)
                val month = cashFlowCalendar.get(Calendar.MONTH)
                val day = cashFlowCalendar.get(Calendar.DAY_OF_MONTH)
                DatePickerDialog(requireContext(),
                    { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                        val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                        binding.updateDate.setText(selectedDate)
                    },
                    year,
                    month,
                    day
                ).show()
        }

        lifecycleScope.launch{
            cashFlowDao.fetchCashFlow(id).collect{
                @Suppress("SENSELESS_COMPARISON")
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

            lifecycleScope.launch{
                cashFlowDao.fetchAllCashFlows().collect{
                    resetRecyclerView()
                }
            }
        }

        builder.setNegativeButton("No"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun setupListOfCashFlowIntoRecyclerView(cashFlowList: ArrayList<CashFlowEntity>){
        if(cashFlowList.isNotEmpty()){

            binding.rvAllHistory.layoutManager = LinearLayoutManager(activity)
            binding.rvAllHistory.adapter = mainAdapter
            mainViewModel.fetchAllCashFlows.observe(viewLifecycleOwner) {
                mainAdapter.setData(it)
            }
        } else {
            Toast.makeText(activity,
                "No Cash Flow found. Please add if you have one.",
                Toast.LENGTH_LONG).show()
        }
    }
    
    private fun setItemAdapter(cashFlowList: ArrayList<CashFlowEntity>,
                               cashFlowDao: CashFlowDao){
        val itemAdapter = CashFlowAdapterHistory(cashFlowList){ updateId ->
            updateRecordDialog(updateId, cashFlowDao)
        }
        mainAdapter = itemAdapter
    }

    private fun resetRecyclerView(){

        binding.rvAllHistory.layoutManager = LinearLayoutManager(activity)
        binding.rvAllHistory.adapter = mainAdapter
    }

    private fun searchRoom(query: String){
        val searchQuery = "%$query%"
        
        binding.rvAllHistory.adapter = mainAdapter

        @Suppress("SENSELESS_COMPARISON","UNNECESSARY_NOT_NULL_ASSERTION")
        if (mainAdapter != null){
            mainViewModel.searchRoom(searchQuery).observe(this) { list ->
                list.let{
                    @Suppress()
                    mainAdapter!!.setData(it)
                }
            }
        } else {
            println("dummy")
        }
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchRoom(query)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

}

