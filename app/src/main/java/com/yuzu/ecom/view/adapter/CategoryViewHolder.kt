package com.yuzu.ecom.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuzu.ecom.databinding.ItemCategoryBinding
import com.yuzu.ecom.model.data.CategoryData
import com.yuzu.ecom.model.data.HomeData

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var binding = ItemCategoryBinding.bind(view)

    fun bind(i: Int, data: List<CategoryData>) {
        Glide.with(itemView).load(data[i].imageUrl).into(binding.photo)

        var name = data[i].name!!
        if (name.length > 6)
            name = "${name.substring(0, 6)}.."
        binding.category.text = name
    }
}