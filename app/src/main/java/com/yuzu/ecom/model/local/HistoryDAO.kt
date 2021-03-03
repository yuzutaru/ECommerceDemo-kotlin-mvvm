package com.yuzu.ecom.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuzu.ecom.model.data.HistoryData
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM HistoryData")
    fun getAllHistory(): Single<List<HistoryData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyData: HistoryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyDataList: List<HistoryData>)
}