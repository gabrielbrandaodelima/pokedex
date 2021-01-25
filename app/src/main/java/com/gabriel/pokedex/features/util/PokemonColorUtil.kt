package com.gabriel.pokedex.features.util

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.PokeTypes

class PokemonColorUtil(var context: Context) {

    @ColorInt
    fun getPokemonColor(typeOfPokemon: List<PokeTypes>?): Int {
        val type = typeOfPokemon?.getOrNull(0)?.type
        val color = when (type?.name?.toLowerCase()) {
            "grass", "bug" -> R.color.lightTeal
            "fire" -> R.color.lightRed
            "water", "fighting", "normal" -> R.color.lightBlue
            "electric", "psychic" -> R.color.lightYellow
            "poison", "ghost" -> R.color.lightPurple
            "ground", "rock" -> R.color.lightBrown
            "dark" -> R.color.black
            else -> R.color.lightBlue
        }
        return convertColor(color)
    }

    @ColorInt
    fun convertColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }
}
