package com.yuzu.ecom.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuzu.ecom.R
import com.yuzu.ecom.model.data.HistoryData

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

class HistoryAdapter(private val data: List<HistoryData>): RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater.inflate(R.layout.item_product_search, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
       holder.bind(position, data)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}