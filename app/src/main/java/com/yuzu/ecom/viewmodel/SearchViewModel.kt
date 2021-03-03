package com.yuzu.ecom.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yuzu.ecom.ECommerceDemoApp
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.model.datasource.ProductDataSourceFactory
import com.yuzu.ecom.model.repository.ProductDBRepository
import com.yuzu.ecom.utils.ARGUMENT_PRODUCT_DATA
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class SearchViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Home"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()
    private val productDBRepository: ProductDBRepository
    private var productDataSourceFactory: ProductDataSourceFactory? = null

    private val product = MutableLiveData<List<ProductPromoData>>()
    fun productDataLive(): LiveData<List<ProductPromoData>> = product

    var productLiveData: LiveData<PagedList<ProductPromoData>>
    var search = MutableLiveData<String>()

    init {
        val appComponent = ECommerceDemoApp.instance.getAppComponent()
        productDBRepository = appComponent.productDBRepository()

        val config = PagedList.Config.Builder().setPageSize(1).setInitialLoadSizeHint(1).setEnablePlaceholders(false).build()
        productLiveData = Transformations.switchMap(search) { input ->
            return@switchMap if (input == null || input.equals("") || input.equals("%%")) {
                //check if the current value is empty load all data else search
                productDataSourceFactory = ProductDataSourceFactory(productDBRepository, compositeDisposable, "")
                LivePagedListBuilder(productDataSourceFactory!!, config).build()
            } else {
                productDataSourceFactory = ProductDataSourceFactory(productDBRepository, compositeDisposable, input)
                LivePagedListBuilder(productDataSourceFactory!!, config).build()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun product(arguments: Bundle?) {
        if (arguments != null) {
            product.value = arguments.get(ARGUMENT_PRODUCT_DATA) as List<ProductPromoData>
        }
    }
}