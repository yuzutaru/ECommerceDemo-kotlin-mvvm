package com.yuzu.ecom.viewmodel

import android.app.Application
import android.content.Intent
import android.view.KeyEvent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yuzu.ecom.view.activity.LoginActivity
import com.yuzu.ecom.view.activity.MainActivity

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class MainMenuViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "MainMenu"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
}