package com.yuzu.ecom.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuzu.ecom.R
import com.yuzu.ecom.databinding.FragmentMainMenuBinding
import com.yuzu.ecom.utils.GOOGLE_REQUEST_ID_TOKEN
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.view.activity.MainActivity
import com.yuzu.ecom.view.adapter.MainMenuViewPagerAdapter
import com.yuzu.ecom.viewmodel.MainMenuViewModel


/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class MainMenuFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModel: MainMenuViewModel

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(MainMenuViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false).apply {
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

        //set ViewPager Adapter
        val titles = resources.getStringArray(R.array.tab_main_menu)
        val adapter = MainMenuViewPagerAdapter(this@MainMenuFragment, titles)
        binding.viewPager.adapter = adapter

        // attaching tab mediator
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
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