package com.yuzu.ecom.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuzu.ecom.databinding.ItemProductBinding
import com.yuzu.ecom.model.data.ProductPromoData

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var binding = ItemProductBinding.bind(view)

    fun bind(i: Int, data: List<ProductPromoData>) {
        Glide.with(itemView).load(data[i].imageUrl).into(binding.photo)

        binding.product.text = data[i].title
    }
}