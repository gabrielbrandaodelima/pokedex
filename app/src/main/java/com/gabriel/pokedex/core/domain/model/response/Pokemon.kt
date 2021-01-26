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

@Parcelize
data class Move(
    @SerializedName("move") val move: BaseResponse? = null
) : Parcelable

@Parcelize
data class Stats(
    @SerializedName("base_stat") val base_stat: Int? = null,
    @SerializedName("effort") val effort: Int? = null,
    @SerializedName("stat") val stat: BaseResponse? = null
) : Parcelable

@Parcelize
data class PokeTypes(
    @SerializedName("type") val type: BaseResponse? = null
) : Parcelable

@Parcelize
data class Sprites(
    @SerializedName("front_default") val front_default: String? = null,
    @SerializedName("other") val other: OtherSprite? = null
) : Parcelable

@Parcelize
data class OtherSprite(
    @SerializedName("official-artwork") val official_artwork: OfficialArtwork? = null
) : Parcelable

@Parcelize
data class OfficialArtwork(
    @SerializedName("front_default") val front_default: String? = null
) : Parcelable