package com.github.sirdeerhead.dailyspending.room

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.CardRowHomeBinding

class CashFlowAdapterHome(
    private var cashFlows: List<CashFlowEntity> = emptyList()
)   :
    RecyclerView.Adapter<CashFlowAdapterHome.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardRowHomeBinding.inflate(
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

        holder.tvDateHome.text = item.date
        holder.tvDescriptionHome.text = item.description
        holder.tvAmountHome.text = amount


        if(item.amount > 0.0){
            holder.tvAmountHome.setTextColor(ContextCompat.getColor(context, R.color.Good))
        } else if (item.amount == 0.0){
            holder.tvAmountHome.setTextColor(ContextCompat.getColor(context, R.color.OnError))
        } else {
            holder.tvAmountHome.setTextColor(ContextCompat.getColor(context, R.color.Error))
        }
    }

    override fun getItemCount(): Int {
        return cashFlows.size
    }

    class ViewHolder(binding: CardRowHomeBinding): RecyclerView.ViewHolder(binding.root){
        val cvHome = binding.cvHome
        val clCardHome = binding.clCardHome
        val tvDateHome = binding.tvDateHome
        val tvDescriptionHome = binding.tvDescriptionHome
        val tvAmountHome = binding.tvAmountHome
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CashFlowEntity>){
        cashFlows = newData
        notifyDataSetChanged()
    }
}