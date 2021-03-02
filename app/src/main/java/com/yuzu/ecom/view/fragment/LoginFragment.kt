package com.yuzu.ecom.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuzu.ecom.databinding.FragmentLoginBinding
import com.yuzu.ecom.utils.GOOGLE_REQUEST_ID_TOKEN
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.viewmodel.LoginViewModel

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginFragment: Fragment() {
    private val LOG_TAG = "Login"
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var auth: FirebaseAuth
    var callbackManager: CallbackManager? = null

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

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
        gLoginOnClick()

        //Facebook login setup
        callbackManager = CallbackManager.Factory.create()
        binding.facebook.fragment = this
        viewModel.facebook(callbackManager!!, binding.facebook)

        //Google login setup
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_REQUEST_ID_TOKEN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

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

    private fun gLoginOnClick() {
        binding.google.setOnClickListener {
            var signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(LOG_TAG, "masuk sini")
        viewModel.checkRequestCode((activity as LoginActivity), callbackManager, requestCode, resultCode, data)
    }
}