package com.yuzu.ecom.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.ItemProductSearchBinding
import com.yuzu.ecom.model.data.ProductPromoData

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductSearchViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var binding = ItemProductSearchBinding.bind(view)

    fun create(parent: ViewGroup): ProductSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_search, parent, false)
        return ProductSearchViewHolder(view)
    }

    fun bind(data: ProductPromoData?) {
        if (data != null) {
            Glide.with(itemView).load(data.imageUrl).into(binding.photo)

            binding.item.text = data.title
            binding.price.text = data.price
        }
    }
}