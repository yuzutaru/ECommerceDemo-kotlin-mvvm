package com.yuzu.ecom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuzu.ecom.model.data.ProductPromoData

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

@Database(entities = [ProductPromoData::class], version = 1)
abstract class ProductDB: RoomDatabase() {
    abstract fun productDao(): ProductDAO
}