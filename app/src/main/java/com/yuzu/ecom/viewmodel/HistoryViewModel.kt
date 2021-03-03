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
import com.yuzu.ecom.model.data.HistoryData
import com.yuzu.ecom.model.repository.HistoryDBRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class HistoryViewModel(app: Application): AndroidViewModel(app) {
    private val LOG_TAG = "Product"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()
    private val historyDBRepository: HistoryDBRepository

    private val history = MutableLiveData<Response<List<HistoryData>>>()
    fun historyDataLive(): LiveData<Response<List<HistoryData>>> = history

    private val historyData = MutableLiveData<List<HistoryData>>()
    fun historyLiveData(): LiveData<List<HistoryData>> = historyData

    init {
        val appComponent = ECommerceDemoApp.instance.getAppComponent()
        historyDBRepository = appComponent.historyDBRepository()
    }

    fun history() {
        compositeDisposable.add(
            historyDBRepository.getAllHistory()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { res ->
                        history.value = Response.succeed(res)
                    },
                    {
                        history.value = when (it) {
                            is NoNetworkException -> {
                                Response.networkLost()
                            }
                            else -> Response.error(it)
                        }
                    }
                )
        )
    }

    fun history(context: Context, resources: Resources, response: Response<List<HistoryData>>) {
        try {
            Log.d(LOG_TAG, "DATA STATUS = ${response.status}")

            if (response.status == Status.SUCCEED) {
                if (response.data != null) {
                    if (!response.data.isNullOrEmpty()) {
                        historyData.value = response.data
                    }
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
                Log.e(LOG_TAG,"errorMessage : ${resources.getString(R.string.no_connection)}")
                Toast.makeText(context, resources.getString(R.string.no_connection), Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(LOG_TAG, it) }
        }
    }
}