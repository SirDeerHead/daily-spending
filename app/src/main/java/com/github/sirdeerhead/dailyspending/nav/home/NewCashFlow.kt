package com.github.sirdeerhead.dailyspending.nav.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.FragmentNewCashFlowBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class NewCashFlow : BottomSheetDialogFragment() {

    private var _binding: FragmentNewCashFlowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewCashFlowBinding.inflate(inflater, container, false)

        val category = resources.getStringArray(R.array.category)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_category_items, category)
        binding.actvDropdownCategory.setAdapter(arrayAdapter)

        binding.date.setOnClickListener{
            cashFlowDatePicker()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun cashFlowDatePicker(){
        val cashFlowCalendar = Calendar.getInstance()
        val year = cashFlowCalendar.get(Calendar.YEAR)
        val month = cashFlowCalendar.get(Calendar.MONTH)
        val day = cashFlowCalendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                binding.date.setText(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }
}