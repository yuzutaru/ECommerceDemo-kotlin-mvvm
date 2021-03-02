package com.yuzu.ecom.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
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

    fun checkRequestCode(activity: LoginActivity, callbackManager: CallbackManager?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.e(LOG_TAG, "masuk sini")
                val account = task.getResult(ApiException::class.java)

                // Signed in successfully, show authenticated UI.
                Log.e(LOG_TAG, "GOOGLE LOGIN SUCCESS = ${account!!.account}")
                Toast.makeText(activity, "google Login success = $account", Toast.LENGTH_LONG).show()

            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w(LOG_TAG, "signInResult:failed code=" + e.statusCode)
            }


        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    /*private fun firebaseAuth(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(LOG_TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }

    }*/
}