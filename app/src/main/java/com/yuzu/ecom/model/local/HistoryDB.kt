package com.yuzu.ecom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuzu.ecom.model.data.HistoryData

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

@Database(entities = [HistoryData::class], version = 1)
abstract class HistoryDB: RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}