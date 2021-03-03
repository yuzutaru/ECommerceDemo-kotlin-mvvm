package com.yuzu.ecom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Product"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
}