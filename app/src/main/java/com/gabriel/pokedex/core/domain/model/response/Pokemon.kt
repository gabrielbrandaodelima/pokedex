package com.gabriel.pokedex.core.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("base_experience") val base_experience: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("weight") val weight: Int? = null,
    @SerializedName("stats") val stats: List<Stats>? = null,
    @SerializedName("sprites") val sprites: Sprites? = null,
    @SerializedName("types") val types: List<PokeTypes>? = null,
    @SerializedName("moves") val moves: List<Move>? = null
) : Parcelable

