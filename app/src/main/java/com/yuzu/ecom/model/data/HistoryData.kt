package com.yuzu.ecom.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Yustar Pramudana on 03/03/2021
 */

@Entity
@Parcelize
data class HistoryData(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("loved")
    var loved: Int? = null
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var historyId: Int = 0
}
