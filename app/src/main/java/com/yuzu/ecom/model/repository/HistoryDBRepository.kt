package com.yuzu.ecom.model.repository

import com.yuzu.ecom.model.data.HistoryData
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

interface HistoryDBRepository {
    fun getAllHistory(): Single<List<HistoryData>>
    fun insert(historyData: HistoryData)
    fun insert(historyDataList: List<HistoryData>)
}