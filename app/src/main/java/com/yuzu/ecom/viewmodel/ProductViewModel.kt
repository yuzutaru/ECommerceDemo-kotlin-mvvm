package com.yuzu.ecom.viewmodel

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuzu.ecom.ECommerceDemoApp
import com.yuzu.ecom.model.data.HistoryData
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.model.repository.HistoryDBRepository
import com.yuzu.ecom.utils.ARGUMENT_PRODUCT_DATA
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Product"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()
    private val historyDBRepository: HistoryDBRepository

    var product = MutableLiveData<ProductPromoData>()
    fun productDataLive(): LiveData<ProductPromoData> = product

    init {
        val appComponent = ECommerceDemoApp.instance.getAppComponent()
        historyDBRepository = appComponent.historyDBRepository()
    }

    fun product(arguments: Bundle?) {
        if (arguments != null) {
            product.value = arguments.get(ARGUMENT_PRODUCT_DATA) as ProductPromoData?
        }
    }

    fun love(isLove: Int?, unlove: ImageView, loved: ImageView) {
        if (isLove != null) {
            if (isLove == 0) {
                unlove.visibility = View.VISIBLE
                loved.visibility = View.GONE
            } else {
                unlove.visibility = View.GONE
                loved.visibility = View.VISIBLE
            }
        }
    }

    fun love(unlove: ImageView, loved: ImageView) {
        if (unlove.visibility == View.VISIBLE) {
            unlove.visibility = View.GONE
        } else {
            unlove.visibility = View.VISIBLE
        }

        if (loved.visibility == View.VISIBLE) {
            loved.visibility = View.GONE
        } else {
            loved.visibility = View.VISIBLE
        }
    }

    fun insertHist() {
        historyDBRepository.insert(HistoryData(product.value!!.id, product.value!!.imageUrl, product.value!!.title, product.value!!.description, product.value!!.price,
            product.value!!.loved))
    }
}