package com.example.mybudget2.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybudget2.R
import com.example.mybudget2.data.History

class CardAdapter(private val items: List<History>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patternTitle: TextView = itemView.findViewById(R.id.pattern)
        val amountTitle: TextView = itemView.findViewById(R.id.amount)
        val dataTitle: TextView = itemView.findViewById(R.id.dada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.patternTitle.text = items[position].pattern
        holder.dataTitle.text = items[position].data
        holder.amountTitle.text = items[position].amount.toString()
    }

    override fun getItemCount(): Int = items.size
}
