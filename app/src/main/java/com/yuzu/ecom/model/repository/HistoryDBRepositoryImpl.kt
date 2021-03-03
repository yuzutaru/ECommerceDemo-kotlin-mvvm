package com.yuzu.ecom.model.repository

import com.yuzu.ecom.model.data.HistoryData
import com.yuzu.ecom.model.local.HistoryDAO
import io.reactivex.Single
import java.util.concurrent.Executor

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

class HistoryDBRepositoryImpl(private val dao: HistoryDAO, private val exec: Executor): HistoryDBRepository {
    override fun getAllHistory(): Single<List<HistoryData>> {
        return dao.getAllHistory()
    }

    override fun insert(historyData: HistoryData) {
        exec.execute {
            dao.insert(historyData)
        }
    }

    override fun insert(historyDataList: List<HistoryData>) {
        exec.execute {
            dao.insert(historyDataList)
        }
    }
}