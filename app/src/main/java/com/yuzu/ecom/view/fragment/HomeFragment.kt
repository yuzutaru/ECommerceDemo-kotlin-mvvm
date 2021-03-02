package com.yuzu.ecom.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.yuzu.ecom.databinding.FragmentHomeBinding
import com.yuzu.ecom.model.data.HomeData
import com.yuzu.ecom.utils.GOOGLE_REQUEST_ID_TOKEN
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.view.activity.MainActivity
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

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

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

        onBackPressed()

        //Google login setup
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_REQUEST_ID_TOKEN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        viewModel.searchOnFocus(activity as MainActivity, binding.search)

        viewModel.home()
        viewModel.homeDataLive().observe(viewLifecycleOwner, {viewModel.homeRes(requireContext(), resources, it)})
        viewModel.homeResDataLive().observe(viewLifecycleOwner, {setRecycler(it)})
    }

    private fun setRecycler(data: HomeData) {
        //set category recycler
        val adapter = CategoryAdapter(data)
        binding.categoryRecyclerView.adapter = adapter
        val horizontalLayout = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.categoryRecyclerView.layoutManager = horizontalLayout

        //set product recycler
        val productAdapter = ProductAdapter(data)
        binding.productRecyclerView.adapter = productAdapter
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.gSignOut(activity as MainActivity, mGoogleSignInClient)
                LoginManager.getInstance().logOut()

                val intent = Intent(activity as MainActivity, LoginActivity::class.java)
                (activity as MainActivity).startActivity(intent)
                (activity as MainActivity).finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}