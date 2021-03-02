package com.yuzu.ecom.viewmodel

import android.app.Application
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.view.activity.MainActivity
import java.util.*


/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Login"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val fbLoginResult = MutableLiveData<LoginResult>()
    fun fbLoginDataLive(): LiveData<LoginResult> =  fbLoginResult

    private val gLoginResult = MutableLiveData<String>()
    fun gLoginDataLive(): LiveData<String> =  gLoginResult

    private val isLoginSuccess = MutableLiveData<Boolean>()
    fun isLoginDataLive(): LiveData<Boolean> = isLoginSuccess

    fun facebook(callbackManager: CallbackManager, facebook: LoginButton) {
        facebook.setPermissions("email", "public_profile")
        facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    Log.e(LOG_TAG, "result = ${result.accessToken}")

                    if (result.accessToken != null && !result.accessToken.isExpired) {
                        isLoginSuccess.value = true

                    } else {
                        fbLoginResult.value = result
                    }
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

    fun fbFirebaseAuth(activity: LoginActivity, auth: FirebaseAuth, token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        unlinkFirebaseAuth(activity, auth, credential)
    }

    fun isFbLoggedIn(activity: LoginActivity) {
        if (Profile.getCurrentProfile() != null && AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut()
        }

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"))
    }

    fun checkRequestCode(activity: LoginActivity, callbackManager: CallbackManager?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 101) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.e(LOG_TAG, "masuk sini")
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    Log.d(LOG_TAG, "GOOGLE LOGIN SUCCESS = ${account.idToken}")
                    gLoginResult.value = account.idToken
                }

            } catch (e: ApiException) {
                Log.w(LOG_TAG, "signInResult:failed code=" + e.statusCode)
            }


        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun GFirebaseAuth(activity: LoginActivity, auth: FirebaseAuth, idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        unlinkFirebaseAuth(activity, auth, credential)
    }

    private fun linkFirebaseAuth(activity: LoginActivity, auth: FirebaseAuth, credential: AuthCredential) {
        if (auth.currentUser != null) {
            auth.currentUser!!.linkWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(LOG_TAG, "linkWithCredential:success")
                        isLoginSuccess.value = true

                    } else {
                        Log.w(LOG_TAG, "linkWithCredential:failure", task.exception)
                        isLoginSuccess.value = false
                    }
                }

        } else {
            firebaseAuth(activity, auth, credential)
        }
    }

    private fun unlinkFirebaseAuth(activity: LoginActivity, auth: FirebaseAuth, credential: AuthCredential) {
        if (auth.currentUser != null) {
            auth.currentUser!!.unlink(credential.provider)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        firebaseAuth(activity, auth, credential)
                    } else {
                        Log.w(LOG_TAG, "unlinkResult:failure", task.exception)
                        linkFirebaseAuth(activity, auth, credential)
                    }
                }

        } else {
            firebaseAuth(activity, auth, credential)
        }
    }

    private fun firebaseAuth(activity: LoginActivity, auth: FirebaseAuth, credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(LOG_TAG, "SignInWithCredential:success")
                    isLoginSuccess.value = true

                } else {
                    Log.w(LOG_TAG, "SignInWithCredential:failure", task.exception)

                    if (task.exception is FirebaseAuthUserCollisionException) {
                        linkFirebaseAuth(activity, auth, credential)
                    }

                    isLoginSuccess.value = false
                }
            }
    }

    fun goToMainMenu(activity: LoginActivity, isLoginSuccess: Boolean) {
        if (isLoginSuccess) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}