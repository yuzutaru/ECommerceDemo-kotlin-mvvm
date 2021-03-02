package com.yuzu.ecom.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuzu.ecom.ECommerceDemoApp
import com.yuzu.ecom.R
import com.yuzu.ecom.model.NoNetworkException
import com.yuzu.ecom.model.Response
import com.yuzu.ecom.model.Status
import com.yuzu.ecom.model.data.Home
import com.yuzu.ecom.model.data.HomeData
import com.yuzu.ecom.model.repository.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

class HomeViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Home"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()

    private val homeRepository: HomeRepository

    private val home = MutableLiveData<Response<List<Home>>>()
    fun homeDataLive(): LiveData<Response<List<Home>>> = home

    private val homeData = MutableLiveData<HomeData>()
    fun homeResDataLive(): LiveData<HomeData> = homeData

    init {
        val appComponent = ECommerceDemoApp.instance.getAppComponent()
        homeRepository = appComponent.homeRepository()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun home() {
        loading.value = true
        compositeDisposable.add(
            homeRepository.home()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        home.value = Response.succeed(res)
                    },
                    {
                        home.value = when (it) {
                            is NoNetworkException -> {
                                Response.networkLost()
                            }
                            else -> Response.error(it)
                        }
                    }
                )
        )
    }

    fun homeRes(context: Context, resources: Resources, response: Response<List<Home>>) {
        try {
            Log.d(LOG_TAG, "DATA STATUS = ${response.status}")

            if (response.status == Status.SUCCEED) {
                if (response.data != null) {
                    if (!response.data.isNullOrEmpty())
                        homeData.value = response.data[0].data
                }

            } else if (response.status == Status.FAILED) {
                if (response.error != null) {
                    if (response.error.message != null) {
                        if (response.error.message!!.contains("Unable to resolve host", true)) {
                            Log.e(LOG_TAG, "errorMessage : ${resources.getString(R.string.no_connection)}")
                            Toast.makeText(context, resources.getString(R.string.no_connection), Toast.LENGTH_LONG).show()

                            loading.value = false

                        } else {
                            Log.e(LOG_TAG, "errorMessage : ${response.error.message}")
                            Toast.makeText(context, response.error.message, Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Log.e(LOG_TAG, "errorMessage : ${resources.getString(R.string.general_error)}")
                        Toast.makeText(context, resources.getString(R.string.general_error), Toast.LENGTH_LONG).show()
                    }
                }

            } else if (response.status == Status.NO_CONNECTION) {
                Log.e(
                    LOG_TAG,
                    "errorMessage : ${resources.getString(R.string.no_connection)}"
                )
                Toast.makeText(context, resources.getString(R.string.no_connection), Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(LOG_TAG, it) }
        }
    }
}