package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtherSprite(
    @SerializedName("official-artwork") val official_artwork: OfficialArtwork? = null
) : Parcelable