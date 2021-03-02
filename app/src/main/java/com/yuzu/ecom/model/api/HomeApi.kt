package com.yuzu.ecom.model.api

import com.yuzu.ecom.model.data.Home
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

interface HomeApi {
    /**
     * Home
     * */
    @GET(value = "home")
    open fun home(): Single<List<Home>>
}