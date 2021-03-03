package com.yuzu.ecom.view.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.FragmentSearchBinding
import com.yuzu.ecom.view.activity.MainActivity
import com.yuzu.ecom.view.adapter.ProductSearchAdapter
import com.yuzu.ecom.viewmodel.SearchViewModel
import java.util.*

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class SearchFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    private lateinit var productAdapter: ProductSearchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecycler()
        initState()
        searchOnTextChanged()

        backOnClick()
        onBackPressed()

        binding.search.requestFocus()
        val imm: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun initState() {
        viewModel.getState().observe(viewLifecycleOwner, { state ->
            viewModel.recyclerViewVisibility(binding, state, productAdapter)
        })
    }

    private fun setRecycler() {
        productAdapter = ProductSearchAdapter {viewModel.retry()}
        binding.recyclerView.adapter = productAdapter
        viewModel.productLiveData.observe(viewLifecycleOwner, {
            try {
                Log.e("Paging ", "PageAll" + it.size)
                try {
                    //to prevent animation recyclerview when change the list
                    binding.recyclerView.itemAnimator = null
                    (Objects.requireNonNull(binding.recyclerView.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations = false
                } catch (e: Exception) {
                    Log.e(LOG_TAG, "productLiveDataError : ${e.message}")
                }
                productAdapter.submitList(it)
            } catch (e: Exception) {
            }
        })
    }

    private fun searchOnTextChanged() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(LOG_TAG, "searchOnTextChanged = ${p0.toString()}")
                viewModel.search.value = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
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