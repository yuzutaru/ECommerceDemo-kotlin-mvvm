package com.yuzu.ecom.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Yustar Pramudana on 28/02/2021
 */

@Parcelize
data class HomeData(
    @SerializedName("category")
    var category: List<CategoryData>? = null,

    @SerializedName("productPromo")
    var productPromo: List<ProductPromoData>? = null
): Parcelable
