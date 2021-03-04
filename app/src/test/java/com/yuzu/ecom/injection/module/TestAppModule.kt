package com.yuzu.ecom.injection.module

import com.yuzu.ecom.ECommerceDemoApp
import com.yuzu.ecom.model.api.HomeApi
import com.yuzu.ecom.model.local.HistoryDAO
import com.yuzu.ecom.model.local.ProductDAO
import com.yuzu.ecom.model.repository.HistoryDBRepository
import com.yuzu.ecom.model.repository.HomeRepository
import com.yuzu.ecom.model.repository.ProductDBRepository
import io.mockk.mockk
import java.util.concurrent.Executor

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

class TestAppModule(application: ECommerceDemoApp): AppModule(application) {
    override fun homeApi(): HomeApi = mockk()
    override fun homeRepository(api: HomeApi): HomeRepository = mockk()
    override fun productDBRepositoryImpl(dao: ProductDAO, exec: Executor): ProductDBRepository = mockk()
    override fun historyDBRepositoryImpl(dao: HistoryDAO, exec: Executor): HistoryDBRepository = mockk()
}