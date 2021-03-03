package com.yuzu.ecom.model.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuzu.ecom.model.State
import com.yuzu.ecom.model.data.ProductPromoData
import com.yuzu.ecom.model.repository.ProductDBRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class ProductDataSource(private val productDBRepository: ProductDBRepository, private val compositeDisposable: CompositeDisposable, private val search: String):
    PageKeyedDataSource<Int, ProductPromoData>() {
    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ProductPromoData>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            productDBRepository.getProductBySearch(search)
                .subscribe(
                    { response ->
                        Log.d("ProductDS", "response = ${response.size}")
                        if (!response.isNullOrEmpty()) {
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
}