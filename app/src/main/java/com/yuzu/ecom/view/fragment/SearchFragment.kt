package com.yuzu.ecom.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.FragmentHomeBinding
import com.yuzu.ecom.databinding.FragmentSearchBinding
import com.yuzu.ecom.utils.GOOGLE_REQUEST_ID_TOKEN
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.view.activity.MainActivity
import com.yuzu.ecom.viewmodel.HomeViewModel
import com.yuzu.ecom.viewmodel.SearchViewModel

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class SearchFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

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

        backOnClick()
        onBackPressed()

        binding.search.requestFocus()
        val imm: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
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