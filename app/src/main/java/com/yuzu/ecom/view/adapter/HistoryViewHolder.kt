package com.yuzu.ecom.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuzu.ecom.databinding.ItemProductSearchBinding
import com.yuzu.ecom.model.data.HistoryData
import com.yuzu.ecom.model.data.ProductPromoData

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var binding = ItemProductSearchBinding.bind(view)

    fun bind(i: Int, data: List<HistoryData>?) {
        if (data != null) {
            Glide.with(itemView).load(data[i].imageUrl).into(binding.photo)

            binding.item.text = data[i].title
            binding.price.text = data[i].price
        }
    }
}