package com.yuzu.ecom.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.SkeletonProductListBinding
import com.yuzu.ecom.model.State

/**
 * Created by Yustar Pramudana on 23/02/2021
 */

class UserListSkeletonViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = SkeletonProductListBinding.bind(view)

    fun create(parent: ViewGroup): UserListSkeletonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skeleton_product_list, parent, false)
        return UserListSkeletonViewHolder(view)
    }

    fun bind(status: State?) {
        binding.skeletonLayout.visibility = if (status == State.LOADING) View.VISIBLE else View.INVISIBLE
        binding.txtError.visibility = if (status == State.ERROR) View.VISIBLE else View.INVISIBLE

        binding.skeletonConstraint.isEnabled = status == State.LOADING
    }
}