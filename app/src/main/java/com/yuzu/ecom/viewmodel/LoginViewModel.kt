package com.yuzu.ecom.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.yuzu.ecom.view.activity.LoginActivity
import java.util.*

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Login"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val loginResult = MutableLiveData<LoginResult>()
    fun loginResultDataLive(): LiveData<LoginResult> =  loginResult

    fun facebook(callbackManager: CallbackManager, facebook: LoginButton) {
        facebook.setPermissions("email", "public_profile")
        facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    Log.e(LOG_TAG, "result = ${result.accessToken}")
                    loginResult.value = result
                }
            }

            override fun onCancel() {
                Log.e(LOG_TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.e(LOG_TAG, "facebook:onError: $error.message")
            }
        })
    }

    fun signCredential(activity: LoginActivity, context: Context, auth: FirebaseAuth, token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(LOG_TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Toast.makeText(activity, "Facebook Login Success: $user", Toast.LENGTH_SHORT).show()
                    //updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(LOG_TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // ...
            }
    }

    fun isFbLoggedIn(activity: LoginActivity) {
        if (Profile.getCurrentProfile() != null && AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance(). logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"))

        } else
            LoginManager.getInstance(). logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"))
    }
}