package com.yuzu.ecom.injection.component

import com.yuzu.ecom.injection.AppInjectTest
import com.yuzu.ecom.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

@Singleton
@Component(modules = [AppModule::class])
interface TestApplicationComponent {
    fun into(test: AppInjectTest)
}