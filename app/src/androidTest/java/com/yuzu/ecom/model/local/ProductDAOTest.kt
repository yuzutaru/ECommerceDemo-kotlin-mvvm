package com.yuzu.ecom.model.local

import com.yuzu.ecom.model.data.ProductPromoData
import org.junit.Assert
import org.junit.Test

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

class ProductDAOTest: ProductDBTest() {
    @Test
    fun getAllProductTest() {
        db.productDAO().insert(ProductPromoData("0"))
        val userDataList = db.productDAO().getAllProduct().blockingGet()
        Assert.assertEquals(userDataList.size, 1)
    }

    @Test
    fun getAllProductListInputTest() {
        db.productDAO().insert(listOf(ProductPromoData("0"), ProductPromoData("1")))
        val userDataList = db.productDAO().getAllProduct().blockingGet()
        Assert.assertEquals(userDataList.size, 2)
    }

    @Test
    fun getAllProductOnConflictTest() {
        db.productDAO().insert(listOf(ProductPromoData("0"), ProductPromoData("0")))
        val userDataList = db.productDAO().getAllProduct().blockingGet()
        Assert.assertEquals(userDataList.size, 1)
    }

    @Test
    fun getProductBySearchTest() {
        db.productDAO().insert(listOf(ProductPromoData("0", "", "arabica"), ProductPromoData("0", "", "robusta")))
        val userDataList = db.productDAO().getProductBySearch("ara").blockingGet()
        Assert.assertEquals(userDataList.size, 1)
    }
}