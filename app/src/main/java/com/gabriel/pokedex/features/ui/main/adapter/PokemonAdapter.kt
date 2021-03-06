package com.gabriel.pokedex.features.ui.main.adapter

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.empty
import com.gabriel.pokedex.core.extensions.getSpannableMatchCase
import com.gabriel.pokedex.core.extensions.loadFromUrl
import com.gabriel.pokedex.core.extensions.visible
import com.gabriel.pokedex.databinding.ItemPokemonBinding
import com.gabriel.pokedex.features.util.PokemonColorUtil

class PokemonAdapter(
    var pokemonArray: ArrayList<Pokemon?>,
    private var clickCallback: (Pokemon?) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    var querySearchText = String.empty()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemonArray[position]
        holder.bindView(item, clickCallback)
    }

    override fun getItemCount(): Int {
        return pokemonArray.size
    }

    internal fun addAll(list: ArrayList<Pokemon?>, query: String? = null) {
        querySearchText = query ?: String.empty()
        pokemonArray = arrayListOf()
        pokemonArray.addAll(list)
        notifyDataSetChanged()
    }

    internal fun appendAll(pokesList: ArrayList<Pokemon>) {
        val startindex = pokemonArray.size
        pokemonArray.addAll(startindex, pokesList)
        notifyItemRangeInserted(startindex, pokesList.size)
    }

    fun clear() {
        pokemonArray.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private val view: ItemPokemonBinding = itemView
        fun bindView(pokemon: Pokemon?, clickCallback: (Pokemon) -> Unit) {
            with(view) {
                when (pokemon) {
                    null -> {
                        itemPokeNameTv.text = pokemon?.name?.capitalize()
                    }
                    else -> {
                        val color =
                            PokemonColorUtil(itemView.context).getPokemonColor(pokemon?.types)
                        itemPokemonFrameLayout.background.colorFilter =
                            PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)


                        when {
                            querySearchText.isBlank() -> {
                                itemPokeNameTv.text = pokemon.name?.capitalize()
                                itemPokeIdTv.alpha = 0.25f
                                itemPokeIdTv.text = itemView.context.getString(
                                    R.string.text_id_pokemon,
                                    pokemon.id.toString()
                                )

                            }
                            else -> {
                                itemPokeNameTv.text = pokemon.name?.capitalize()
                                    .getSpannableMatchCase(itemView.context, querySearchText, true)
                                itemPokeIdTv.alpha = 1.0f
                                itemPokeIdTv.text = itemView.context.getString(
                                    R.string.text_id_pokemon,
                                    pokemon.id.toString()
                                ).getSpannableMatchCase(itemView.context, querySearchText, true)

                            }
                        }


                        itemPokeImageView.loadFromUrl(pokemon?.sprites?.other?.official_artwork?.front_default)
                        pokemon?.types?.forEachIndexed { index, pokeTypes ->
                            when (index) {
                                0 -> {
                                    itemPokeType1Tv.text = pokeTypes.type?.name?.capitalize()
                                    itemPokeType1Tv.visible()
                                }
                                1 -> {
                                    itemPokeType2Tv.text = pokeTypes.type?.name?.capitalize()
                                    itemPokeType2Tv.visible()
                                }
                                2 -> {
                                    itemPokeType3Tv.text = pokeTypes.type?.name?.capitalize()
                                    itemPokeType3Tv.visible()
                                }
                            }
                        }
                        itemPokemonFrameLayout.setOnClickListener { clickCallback(pokemon) }
                    }
                }
            }
        }
    }

}
