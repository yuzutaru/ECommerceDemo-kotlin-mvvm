package com.yuzu.ecom.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryData(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null,
    @SerializedName("name")
    var name: String? = null
): Parcelable