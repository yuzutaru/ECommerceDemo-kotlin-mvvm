package com.yuzu.ecom.injection.component

import android.app.Application
import com.yuzu.ecom.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)
}