package com.yuzu.ecom.model.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuzu.ecom.model.State
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.model.repository.ProductDBRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductDataSource(private val productDBRepository: ProductDBRepository, private val compositeDisposable: CompositeDisposable, private val search: String):
    PageKeyedDataSource<Int, ProductPromoData>() {
    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ProductPromoData>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            productDBRepository.getProductBySearch(search)
                .subscribe(
                    { response ->
                        if (!response.isNullOrEmpty()) {
                            Log.d("ProductDS", "response = ${response.size}")
                            updateState(State.DONE)
                            callback.onResult(response,
                                null,
                                0,
                            )

                        } else {

                        }
                    },
                    {

                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ProductPromoData>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ProductPromoData>) {
        updateState(State.DONE)
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }
}