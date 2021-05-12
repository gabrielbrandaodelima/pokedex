package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Move(
    @SerializedName("move") val move: BaseResponse? = null
) : Parcelable