package com.yuzu.ecom.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Yustar Pramudana on 02/03/2021
 */

@Parcelize
data class Home(
    @SerializedName("data")
    var data: HomeData? = null
):Parcelable