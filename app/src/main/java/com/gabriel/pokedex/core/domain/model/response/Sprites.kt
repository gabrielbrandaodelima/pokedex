package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sprites(
    @SerializedName("front_default") val front_default: String? = null,
    @SerializedName("other") val other: OtherSprite? = null
) : Parcelable