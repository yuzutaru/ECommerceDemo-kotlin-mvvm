package com.yuzu.ecom.model.repository

import com.yuzu.ecom.model.api.HomeApi
import com.yuzu.ecom.model.data.Home
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class HomeRepositoryImpl(private val api: HomeApi): HomeRepository {
    override fun home(): Single<List<Home>> {
        return api.home()
    }
}