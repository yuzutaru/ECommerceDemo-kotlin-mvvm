package com.yuzu.ecom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class LoginViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Login"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
}