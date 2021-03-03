package com.yuzu.ecom.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.model.repository.ProductDBRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductDataSourceFactory(private val productDBRepository: ProductDBRepository, private val compositeDisposable: CompositeDisposable, private val search: String):
    DataSource.Factory<Int, ProductPromoData>() {
    val productLiveData = MutableLiveData<ProductDataSource>()

    override fun create(): DataSource<Int, ProductPromoData> {
        val productDataSource = ProductDataSource(productDBRepository, compositeDisposable, search)
        productLiveData.postValue(productDataSource)
        return productDataSource
    }
}