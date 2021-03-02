package com.yuzu.ecom.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuzu.ecom.R
import com.yuzu.ecom.model.data.HomeData
import com.yuzu.ecom.model.data.ProductPromoData

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class ProductAdapter(private val data: HomeData): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        data.productPromo?.let { holder.bind(position, it) }
    }

    override fun getItemCount(): Int {
        return data.productPromo!!.size
    }
}