package com.yuzu.ecom.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.utils.ARGUMENT_PRODUCT_DATA

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Product"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    var product = MutableLiveData<ProductPromoData>()
    fun productDataLive(): LiveData<ProductPromoData> = product

    fun product(arguments: Bundle?) {
        if (arguments != null) {
            product.value = arguments.get(ARGUMENT_PRODUCT_DATA) as ProductPromoData?
        }
    }
}