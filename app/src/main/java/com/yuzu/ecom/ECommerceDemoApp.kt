package com.yuzu.ecom

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.yuzu.ecom.injection.component.AppComponent
import com.yuzu.ecom.injection.component.DaggerAppComponent
import com.yuzu.ecom.injection.module.AppModule

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

class ECommerceDemoApp: Application() {
    lateinit var component: AppComponent

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: ECommerceDemoApp private set
    }

    operator fun get(context: Context): ECommerceDemoApp {
        return context.applicationContext as ECommerceDemoApp
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }

    @Suppress("DEPRECATION")
    override fun onCreate() {
        super.onCreate()
        instance = this
        // DI
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
    }
}