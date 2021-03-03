package com.yuzu.ecom.model.repository

import com.yuzu.ecom.model.data.ProductPromoData
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

interface ProductDBRepository {
    fun getProductBySearch(search: String): Single<List<ProductPromoData>>
    fun getAllProduct(): Single<List<ProductPromoData>>
    fun insert(productData: ProductPromoData)
    fun insert(productDataList: List<ProductPromoData>)
}