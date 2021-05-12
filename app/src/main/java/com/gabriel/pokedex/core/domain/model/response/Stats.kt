package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stats(
    @SerializedName("base_stat") val base_stat: Int? = null,
    @SerializedName("effort") val effort: Int? = null,
    @SerializedName("stat") val stat: BaseResponse? = null
) : Parcelable