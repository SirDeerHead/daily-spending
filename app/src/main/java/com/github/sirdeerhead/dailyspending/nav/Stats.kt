package com.github.sirdeerhead.dailyspending.nav

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.sirdeerhead.dailyspending.databinding.FragmentStatsBinding
import com.github.sirdeerhead.dailyspending.room.CashFlowApp
import com.github.sirdeerhead.dailyspending.room.CashFlowDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class Stats : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieChartIncomes : PieChart
    private lateinit var pieChartExpenses : PieChart
    private lateinit var cashFlowDao : CashFlowDao

    private var colorsExpenses : MutableList<Int?> = mutableListOf(
        Color.parseColor("#7755FF"), Color.parseColor("#118833"),
        Color.parseColor("#777766"), Color.parseColor("#5577aa"),
        Color.parseColor("#dd3322"), Color.parseColor("#3377cc"),
        Color.parseColor("#996688"), Color.parseColor("#bb33dd")
    )

    private var colorsIncomes : MutableList<Int?> = mutableListOf(
        Color.parseColor("#6666dd"), Color.parseColor("#008855"),
        Color.parseColor("#aa6600"), Color.parseColor("#1177cc"),
        Color.parseColor("#ee0000"), Color.parseColor("#4477bb"),
        Color.parseColor("#996677"), Color.parseColor("#aa55aa")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        cashFlowDao = (activity?.application as CashFlowApp).database.cashFlowDao()

        pieChartExpenses = binding.pcPieChartExpenses
        pieChartIncomes = binding.pcPieChartIncomes

        setPieChartExpenses()
        setPieChartIncomes()

        return binding.root
    }

    private fun setPieChartExpenses() {
        val categoryExpensesList: ArrayList<PieEntry> = ArrayList()

        lifecycleScope.launch{
            val listOfExpenses = cashFlowDao.countedExpensesCategory()

            for (category in listOfExpenses){
                categoryExpensesList.add(PieEntry(category.totalExpensesCount, category.expensesCategory))
            }

            val pieDataSet = PieDataSet(categoryExpensesList,"Expenses")

            pieDataSet.colors = colorsExpenses

            pieDataSet.valueTextSize = 18f

            pieDataSet.valueTextColor = Color.BLACK

            val pieData = PieData(pieDataSet)

            pieChartExpenses.data = pieData

            pieChartExpenses.legend.isEnabled = false

            pieChartExpenses.description.isEnabled = false

            pieChartExpenses.centerText = "Expenses"

            pieChartExpenses.animateY(850)
        }

    }

    private fun setPieChartIncomes() {
        val categoryIncomesList: ArrayList<PieEntry> = ArrayList()

        lifecycleScope.launch{
            val listOfIncomes = cashFlowDao.countedIncomesCategory()

            for (category in listOfIncomes){
                categoryIncomesList.add(PieEntry(category.totalIncomesCount, category.incomesCategory))
            }

            val pieDataSet = PieDataSet(categoryIncomesList,"Incomes")

            pieDataSet.colors = colorsIncomes

            pieDataSet.valueTextSize = 18f

            pieDataSet.valueTextColor = Color.BLACK

            val pieData = PieData(pieDataSet)

            pieChartIncomes.data = pieData

            pieChartIncomes.legend.isEnabled = false

            pieChartIncomes.description.isEnabled = false

            pieChartIncomes.centerText = "Incomes"

            pieChartIncomes.animateY(850)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}