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
import com.yuzu.ecom.databinding.FragmentHistoryBinding
import com.yuzu.ecom.model.data.HistoryData
import com.yuzu.ecom.view.activity.MainActivity
import com.yuzu.ecom.view.adapter.HistoryAdapter
import com.yuzu.ecom.viewmodel.HistoryViewModel

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

class HistoryFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backOnClick()
        onBackPressed()

        viewModel.history()
        viewModel.historyDataLive().observe(viewLifecycleOwner, { viewModel.history(requireContext(), resources, it) })
        viewModel.historyLiveData().observe(viewLifecycleOwner, { setRecycler(it) })
    }

    private fun setRecycler(data: List<HistoryData>) {
        val adapter = HistoryAdapter(data)
        binding.recyclerView.adapter = adapter
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