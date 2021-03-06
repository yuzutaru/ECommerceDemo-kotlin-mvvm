package com.yuzu.ecom.injection.module

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.yuzu.ecom.model.api.HomeApi
import com.yuzu.ecom.model.local.HistoryDAO
import com.yuzu.ecom.model.local.HistoryDB
import com.yuzu.ecom.model.local.ProductDAO
import com.yuzu.ecom.model.local.ProductDB
import com.yuzu.ecom.model.repository.*
import com.yuzu.ecom.utils.BASE_URL
import com.yuzu.ecom.utils.TIMEOUT_HTTP
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

@Module
open class AppModule(private val app: Application) {
    @Provides
    fun app(): Application {
        return app
    }

    private fun provideOkHttpClient(): OkHttpClient {
        try {
            // Create a trust manager that does not validate certificate chains

            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @SuppressLint("TrustAllX509TrustManager")
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            var builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })

            var loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return builder.addInterceptor(loggingInterceptor)
                .readTimeout(TIMEOUT_HTTP.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_HTTP.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_HTTP.toLong(), TimeUnit.SECONDS)
                .build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    //Home Api
    @Provides
    @Singleton
    open fun homeApi(): HomeApi {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    open fun homeRepository(api: HomeApi): HomeRepository {
        return HomeRepositoryImpl(api)
    }

    //Product ROOM
    @Provides
    @Singleton
    fun productDB(): ProductDB {
        return Room.databaseBuilder(app, ProductDB::class.java, "product.db").build()
    }

    @Provides
    @Singleton
    fun productDAO(db: ProductDB): ProductDAO {
        return db.productDAO()
    }

    @Provides
    @Singleton
    open fun productDBRepositoryImpl(dao: ProductDAO, exec: Executor): ProductDBRepository {
        return ProductDBRepositoryImpl(dao, exec)
    }

    //History ROOM
    @Provides
    @Singleton
    fun historyDB(): HistoryDB {
        return Room.databaseBuilder(app, HistoryDB::class.java, "history.db").build()
    }

    @Provides
    @Singleton
    fun historyDAO(db: HistoryDB): HistoryDAO {
        return db.historyDAO()
    }

    @Provides
    @Singleton
    open fun historyDBRepositoryImpl(dao: HistoryDAO, exec: Executor): HistoryDBRepository {
        return HistoryDBRepositoryImpl(dao, exec)
    }

    @Singleton
    @Provides
    fun getExecutor(): Executor {
        return Executors.newFixedThreadPool(2)
    }
}