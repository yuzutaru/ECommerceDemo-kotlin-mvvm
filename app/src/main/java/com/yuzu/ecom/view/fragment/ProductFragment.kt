package com.yuzu.ecom.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.FragmentProductBinding
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.view.activity.MainActivity
import com.yuzu.ecom.viewmodel.ProductViewModel

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentProductBinding
    private lateinit var viewModel: ProductViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loveOnClick()
        backOnClick()
        onBackPressed()

        viewModel.product(arguments)
        viewModel.productDataLive().observe(viewLifecycleOwner, { setProduct(it) })
    }

    private fun setProduct(data: ProductPromoData) {
        Glide.with(requireContext()).load(data.imageUrl).into(binding.photo)
        binding.item.text = data.title
        binding.desc.text = data.description
        viewModel.love(data.loved, binding.unlove, binding.loved)
    }

    private fun loveOnClick() {
        binding.unlove.setOnClickListener {
            viewModel.love(binding.unlove, binding.loved)
        }

        binding.loved.setOnClickListener {
            viewModel.love(binding.unlove, binding.loved)
        }
    }

    private fun backOnClick() {
        binding.back.setOnClickListener {
            (activity as MainActivity).replaceFragment(R.id.main_content, MainMenuFragment(), null)
        }
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).replaceFragment(R.id.main_content, MainMenuFragment(), null)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}