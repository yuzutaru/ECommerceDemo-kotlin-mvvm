package com.yuzu.ecom.injection

import com.yuzu.ecom.ECommerceDemoApp
import com.yuzu.ecom.injection.component.DaggerTestApplicationComponent
import com.yuzu.ecom.injection.module.TestAppModule
import com.yuzu.ecom.model.api.HomeApi
import com.yuzu.ecom.model.data.*
import com.yuzu.ecom.model.repository.HistoryDBRepository
import com.yuzu.ecom.model.repository.HomeRepository
import com.yuzu.ecom.model.repository.ProductDBRepository
import io.mockk.every
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

class AppInjectTest {
    @Inject
    lateinit var api: HomeApi

    @Inject
    lateinit var homeRepo: HomeRepository

    @Inject
    lateinit var productDBRepo: ProductDBRepository

    @Inject
    lateinit var historyDBRepository: HistoryDBRepository

    @Before
    fun setUp() {
        val component = DaggerTestApplicationComponent.builder()
            .appModule(TestAppModule(ECommerceDemoApp()))
            .build()
        component.into(this)
    }

    @Test
    fun homeAPITest() {
        Assert.assertNotNull(api)
        every { api.home() } returns Single.just(listOf(Home(HomeData(listOf(CategoryData(0)), listOf(ProductPromoData("0"))))))
        val result = api.home()
        result.test().assertValue(listOf(Home(HomeData(listOf(CategoryData(0)), listOf(ProductPromoData("0"))))))
    }

    @Test
    fun homeRepoTest() {
        Assert.assertNotNull(homeRepo)
        every { homeRepo.home() } returns Single.just(listOf(Home(HomeData(listOf(CategoryData(0)), listOf(ProductPromoData("0"))))))
        val result = homeRepo.home()
        result.test().assertValue(listOf(Home(HomeData(listOf(CategoryData(0)), listOf(ProductPromoData("0"))))))
    }

    @Test
    fun productDBRepoGetAllProductTest() {
        Assert.assertNotNull(productDBRepo)
        every { productDBRepo.getAllProduct() } returns Single.just(listOf(ProductPromoData("0")))
        val result = productDBRepo.getAllProduct()
        result.test().assertValue(listOf(ProductPromoData("0")))
    }

    @Test
    fun productDBRepoGetProductBySearchTest() {
        Assert.assertNotNull(productDBRepo)
        every { productDBRepo.getProductBySearch("k") } returns Single.just(listOf(ProductPromoData("0", "", "kopi")))
        val result = productDBRepo.getProductBySearch("k")
        result.test().assertValue(listOf(ProductPromoData("0", "", "kopi")))
    }

    @Test
    fun historyDBTest() {
        Assert.assertNotNull(historyDBRepository)
        every { historyDBRepository.getAllHistory() } returns Single.just(listOf(HistoryData()))
        val result = historyDBRepository.getAllHistory()
        result.test().assertValue(listOf(HistoryData()))
    }
}