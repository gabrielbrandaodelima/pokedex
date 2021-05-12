package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfficialArtwork(
    @SerializedName("front_default") val front_default: String? = null
) : Parcelable