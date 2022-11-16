package com.github.sirdeerhead.dailyspending.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.sirdeerhead.dailyspending.databinding.ItemsRowHistoryBinding

class CashFlowAdapter(private val cashFlows: ArrayList<CashFlowEntity>,
                      private val updateListener:(id:Int)->Unit,
                      private val deleteListener:(id:Int)->Unit,
                      ): RecyclerView.Adapter<CashFlowAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemsRowHistoryBinding): RecyclerView.ViewHolder(binding.root){
        val llItemsRowHistory = binding.llItemsRowHistory
        val tvDate = binding.tvDate
        val tvCategory = binding.tvCategory
        val tvDescription = binding.tvDescription
        val tvAmount = binding.tvAmount
        val ivItemsRowHistoryEdit = binding.ivItemsRowHistoryEdit
        val ivItemsRowHistoryDelete = binding.ivItemsRowHistoryDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemsRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val items = cashFlows[position]
    }

    override fun getItemCount(): Int {
        return cashFlows.size
    }
}