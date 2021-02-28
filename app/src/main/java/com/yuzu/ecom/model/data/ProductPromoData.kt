package com.yuzu.ecom.model.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductPromoData(
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
): Parcelable