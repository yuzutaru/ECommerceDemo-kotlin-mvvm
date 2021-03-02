package com.yuzu.ecom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Home"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
}