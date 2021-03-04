package com.yuzu.ecom.model.local

import com.yuzu.ecom.model.data.HistoryData
import com.yuzu.ecom.model.data.ProductPromoData
import org.junit.Assert
import org.junit.Test

/**
 * Created by Yustar Pramudana on 04/03/2021
 */

class HistoryDAOTest: HistoryDBTest() {
    @Test
    fun getAllHistoryTest() {
        db.historyDAO().insert(HistoryData())
        val userDataList = db.historyDAO().getAllHistory().blockingGet()
        Assert.assertEquals(userDataList.size, 1)
    }

    @Test
    fun getAllHistoryListInputTest() {
        db.historyDAO().insert(listOf(HistoryData(), HistoryData()))
        val userDataList = db.historyDAO().getAllHistory().blockingGet()
        Assert.assertEquals(userDataList.size, 2)
    }
}