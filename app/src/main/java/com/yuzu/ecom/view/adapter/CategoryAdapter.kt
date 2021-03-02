package com.yuzu.ecom.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuzu.ecom.R
import com.yuzu.ecom.model.data.HomeData
import com.yuzu.ecom.viewmodel.HomeViewModel

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class CategoryAdapter(private val data: HomeData): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(inflater.inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        data.category?.let { holder.bind(position, it) }
    }

    override fun getItemCount(): Int {
        return data.category!!.size
    }
}