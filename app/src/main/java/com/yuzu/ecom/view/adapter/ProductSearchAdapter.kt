package com.yuzu.ecom.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yuzu.ecom.R
import com.yuzu.ecom.model.State
import com.yuzu.ecom.model.data.ProductPromoData

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductSearchAdapter():
    PagedListAdapter<ProductPromoData, RecyclerView.ViewHolder>(UserDiffCallback) {
    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    private var state = State.LOADING

    companion object {
        val UserDiffCallback = object : DiffUtil.ItemCallback<ProductPromoData>() {
            override fun areItemsTheSame(oldItem: ProductPromoData, newItem: ProductPromoData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductPromoData, newItem: ProductPromoData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_product_search, parent, false)

        return ProductSearchViewHolder(view).create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductSearchViewHolder).bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}