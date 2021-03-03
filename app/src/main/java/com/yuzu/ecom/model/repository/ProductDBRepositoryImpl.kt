package com.yuzu.ecom.model.repository

import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.model.local.ProductDAO
import io.reactivex.Single
import java.util.concurrent.Executor

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductDBRepositoryImpl(private val dao: ProductDAO, private val exec: Executor): ProductDBRepository {
    override fun getProductBySearch(search: String): Single<List<ProductPromoData>> {
        return dao.getProductBySearch(search)
    }

    override fun getAllProduct(): Single<List<ProductPromoData>> {
        return dao.getAllProduct()
    }

    override fun insert(productData: ProductPromoData) {
        exec.execute {
            dao.insert(productData)
        }
    }

    override fun insert(productDataList: List<ProductPromoData>) {
        exec.execute {
            dao.insert(productDataList)
        }
    }
}