package com.yuzu.ecom.injection.component

import android.app.Application
import com.yuzu.ecom.injection.module.AppModule
import com.yuzu.ecom.model.api.HomeApi
import com.yuzu.ecom.model.local.HistoryDAO
import com.yuzu.ecom.model.local.HistoryDB
import com.yuzu.ecom.model.local.ProductDAO
import com.yuzu.ecom.model.local.ProductDB
import com.yuzu.ecom.model.repository.HistoryDBRepository
import com.yuzu.ecom.model.repository.HomeRepository
import com.yuzu.ecom.model.repository.ProductDBRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)

    //Home Api
    fun homeApi(): HomeApi
    fun homeRepository(): HomeRepository

    //Product ROOM Data
    fun productDB(): ProductDB
    fun productDAO(): ProductDAO
    fun productDBRepository(): ProductDBRepository

    //History ROOM Data
    fun historyDB(): HistoryDB
    fun historyDAO(): HistoryDAO
    fun historyDBRepository(): HistoryDBRepository
}