package com.yuzu.ecom.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuzu.ecom.model.data.ProductPromoData
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

@Dao
interface ProductDAO {
    @Query("SELECT * FROM ProductPromoData WHERE ProductPromoData.title like :search||'%'")
    fun getProductBySearch(search: String): Single<List<ProductPromoData>>

    @Query("SELECT * FROM ProductPromoData")
    fun getAllProduct(): Single<List<ProductPromoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productData: ProductPromoData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productDataList: List<ProductPromoData>)
}