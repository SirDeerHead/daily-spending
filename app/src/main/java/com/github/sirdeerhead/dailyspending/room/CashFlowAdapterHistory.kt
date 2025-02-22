package com.github.sirdeerhead.dailyspending.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.CardRowHistoryBinding

class CashFlowAdapterHistory(
    private var cashFlows: List<CashFlowEntity> = emptyList(),
    private val updateListener: (id:Int) -> Unit
)   :
    RecyclerView.Adapter<CashFlowAdapterHistory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardRowHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = cashFlows[position]
        var amount = item.amount.toString()

        val sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preference = sharedPreferences?.getString("CURRENCY_KEY", "$")
        amount = "$amount $preference"


        holder.tvDate.text = item.date
        holder.tvDescription.text = item.description
        holder.tvAmount.text = amount

        // Text coloring by the value
        if(item.amount > 0.0){
            // Green - Income
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.Good))
        } else if (item.amount == 0.0){
            // Black - Neutral / Informational
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.OnError))
        } else {
            // Red - Expense
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.Error))
        }

        holder.ivEditButton.setOnClickListener{
            updateListener(item.id)
        }
    }

    override fun getItemCount(): Int {
        return cashFlows.size
    }

    class ViewHolder(binding: CardRowHistoryBinding): RecyclerView.ViewHolder(binding.root){
        val tvDate = binding.tvDate
        val tvDescription = binding.tvDescription
        val tvAmount = binding.tvAmount
        val ivEditButton = binding.ivEditButton
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CashFlowEntity>){
        cashFlows = newData
        notifyDataSetChanged()
    }
}