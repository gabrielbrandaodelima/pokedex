package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonListingResponse(
    @SerializedName("results") val results: List<PokemonListing>? = null,
    @SerializedName("next") val next: String? = null
) : Parcelable

