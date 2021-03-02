package com.yuzu.ecom.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.yuzu.ecom.view.activity.MainActivity

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class MainMenuViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "MainMenu"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun gSignOut(activity: MainActivity, googleSignInClient: GoogleSignInClient) {
        googleSignInClient.signOut()
            .addOnCompleteListener(activity, object : OnCompleteListener<Void> {
                override fun onComplete(p0: Task<Void>) {
                    Log.d(LOG_TAG, "google signedOut")
                }
            })

    }
}