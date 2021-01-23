package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseResponse (
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
) : Parcelable