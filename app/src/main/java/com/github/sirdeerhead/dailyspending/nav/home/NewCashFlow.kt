package com.github.sirdeerhead.dailyspending.nav.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.FragmentNewCashFlowBinding
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class NewCashFlow : BottomSheetDialogFragment() {

    private var _binding: FragmentNewCashFlowBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val category = resources.getStringArray(R.array.category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_category_items, category)
        binding.actvDropdownCategory.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewCashFlowBinding.inflate(inflater, container, false)

        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()
        binding.addCashFlow.setOnClickListener{
            addRecord(cashFlowDao)
        }

        binding.tietDate.setOnClickListener{
            cashFlowDatePicker()
        }

        return binding.root
    }

    private fun addRecord(cashFlowDao: CashFlowDao){
        val date = binding.tietDate.text.toString()
        val amount = binding.tietAmount.text.toString()
        val category = binding.actvDropdownCategory.text.toString()
        val description = binding.tietDescription.text.toString()

        if(date.isNotEmpty() && amount.isNotEmpty()
            && category.isNotEmpty() && description.isNotEmpty()){

            lifecycleScope.launch {
                cashFlowDao.insert(
                    CashFlowEntity(
                        date = date, amount = amount.toDouble(),
                        category = category, description = description
                    ))

                Toast.makeText(activity, "Cash Flow added", Toast.LENGTH_LONG).show()

                binding.tietDate.text?.clear()
                binding.tietAmount.text?.clear()
                binding.actvDropdownCategory.text?.clear()
                binding.tietDescription.text?.clear()
            }
        } else {
            Toast.makeText(activity,
                "Date, amount, category and description cannot be blank",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun cashFlowDatePicker(){
        val cashFlowCalendar = Calendar.getInstance()
        val year = cashFlowCalendar.get(Calendar.YEAR)
        val month = cashFlowCalendar.get(Calendar.MONTH)
        val day = cashFlowCalendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                binding.tietDate.setText(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}