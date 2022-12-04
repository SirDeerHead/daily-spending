package com.github.sirdeerhead.dailyspending.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.CardRowHistoryBinding

class CashFlowAdapter(
    private var cashFlows: List<CashFlowEntity> = emptyList(),
    private val updateListener: (id:Int) -> Unit
)   :
    RecyclerView.Adapter<CashFlowAdapter.ViewHolder>() {

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

        holder.tvDate.text = item.date
        holder.tvDescription.text = item.description
        holder.tvAmount.text = item.amount.toString()

        if(item.amount > 0.0){
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.Good))
        } else if (item.amount == 0.0){
            holder.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.OnError))
        } else {
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
        val cvHistory = binding.cvHistory
        val clCardHistory = binding.clCardHistory
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