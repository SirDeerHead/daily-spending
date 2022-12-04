package com.github.sirdeerhead.dailyspending.nav.history

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import com.github.sirdeerhead.dailyspending.room.CashFlowEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import com.github.sirdeerhead.dailyspending.databinding.FragmentUpdateCashFlowBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class UpdateCashFlow : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdateCashFlowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateCashFlowBinding.inflate(inflater, container, false)

        val category = resources.getStringArray(R.array.category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_category_items, category)
        binding.updateDropdownCategory.setAdapter(arrayAdapter)

        val cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()
        binding.updateCashFlow.setOnClickListener{
            updateRecord(cashFlowDao)
        }

        binding.updateDate.setOnClickListener{
            cashFlowDatePicker()
        }

        return binding.root
    }

    private fun updateRecord(cashFlowDao: CashFlowDao){
        val date = binding.updateDate.text.toString()
        val amount = binding.updateAmount.text.toString()
        val category = binding.updateDropdownCategory.text.toString()
        val description = binding.updateDescription.text.toString()

        if(date.isNotEmpty() && amount.isNotEmpty()
            && category.isNotEmpty() && description.isNotEmpty()){

            lifecycleScope.launch {
                cashFlowDao.insert(
                    CashFlowEntity(
                        date = date, amount = amount.toDouble(),
                        category = category, description = description
                    )
                )

                Toast.makeText(activity, "Cash Flow added", Toast.LENGTH_LONG).show()

                binding.updateDate.text?.clear()
                binding.updateAmount.text?.clear()
                binding.updateDropdownCategory.text?.clear()
                binding.updateDescription.text?.clear()
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
                binding.updateDate.setText(selectedDate)
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