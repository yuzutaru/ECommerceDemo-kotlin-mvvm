package com.yuzu.ecom.model.repository

import com.yuzu.ecom.model.data.Home
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

interface HomeRepository {
    fun home(): Single<List<Home>>
}