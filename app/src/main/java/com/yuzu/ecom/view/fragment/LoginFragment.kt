package com.yuzu.ecom.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuzu.ecom.databinding.FragmentLoginBinding
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.viewmodel.LoginViewModel
import java.util.*

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var auth: FirebaseAuth
    var callbackManager: CallbackManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        auth = Firebase.auth
        fbFrameOnClick()
        fbLoginOnClick()

        callbackManager = CallbackManager.Factory.create()
        binding.facebook.fragment = this
        viewModel.facebook(callbackManager!!, binding.facebook)

        viewModel.loginResultDataLive().observe(viewLifecycleOwner, {viewModel.signCredential(activity as LoginActivity, requireContext(), auth, it.accessToken)})
    }

    private fun fbFrameOnClick() {
        binding.facebookFrame.setOnClickListener {
            binding.facebook.performClick()
        }
    }

    private fun fbLoginOnClick() {
        binding.facebook.setOnClickListener {
            viewModel.isFbLoggedIn(activity as LoginActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}