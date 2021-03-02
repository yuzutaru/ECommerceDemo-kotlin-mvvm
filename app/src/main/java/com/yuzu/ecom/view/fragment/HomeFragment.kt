package com.yuzu.ecom.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuzu.ecom.databinding.FragmentHomeBinding
import com.yuzu.ecom.model.data.HomeData
import com.yuzu.ecom.view.adapter.CategoryAdapter
import com.yuzu.ecom.view.adapter.ProductAdapter
import com.yuzu.ecom.viewmodel.HomeViewModel

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class HomeFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.home()
        viewModel.homeDataLive().observe(viewLifecycleOwner, {viewModel.homeRes(requireContext(), resources, it)})
        viewModel.homeResDataLive().observe(viewLifecycleOwner, {setRecycler(it)})
    }

    private fun setRecycler(data: HomeData) {
        val adapter = CategoryAdapter(data)
        binding.categoryRecyclerView.adapter = adapter
        var horizontalLayout = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.categoryRecyclerView.layoutManager = horizontalLayout

        val productAdapter = ProductAdapter(data)
        binding.productRecyclerView.adapter = productAdapter
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}