package com.yuzu.ecom.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.FragmentProductBinding
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

        onBackPressed()
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