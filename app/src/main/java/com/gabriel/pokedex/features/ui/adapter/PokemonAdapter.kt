package com.gabriel.pokedex.features.ui.adapter

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.extensions.loadFromUrl
import com.gabriel.pokedex.core.extensions.visible
import com.gabriel.pokedex.databinding.ItemPokemonBinding
import com.gabriel.pokedex.features.util.PokemonColorUtil

class PokemonAdapter(
    private var pokemonPairList: ArrayList<Pair<PokemonListing, Pokemon?>>,
    private var clickCallback: (Pokemon?) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    var holder: ViewHolder? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.holder = holder
        val item = pokemonPairList[position]
        holder.bindView(item, clickCallback)
    }

    override fun getItemCount(): Int {
        return pokemonPairList.size
    }

    internal fun addAll(list: ArrayList<PokemonListing>) {
        pokemonPairList = arrayListOf<Pair<PokemonListing, Pokemon?>>()
        list.forEach {
            pokemonPairList.add(Pair(it, null))
        }
        notifyDataSetChanged()
    }

    internal fun appendAll(pokesList: ArrayList<PokemonListing>) {
        val startindex = pokemonPairList.size
        var arrayPair = arrayListOf<Pair<PokemonListing,Pokemon?>>()
        pokesList.forEach {
            arrayPair.add(Pair(it, null))
        }
        pokemonPairList.addAll(startindex, arrayPair)
        notifyItemRangeInserted(startindex, pokesList.size)
    }

    fun clear() {
        pokemonPairList.clear()
        notifyDataSetChanged()
    }

    fun loadPokemonsInfo(pokemonList: ArrayList<Pokemon>?) {
        pokemonList?.forEach { pokemonDetail ->
            pokemonPairList.forEachIndexed { index, pair ->
                if (pokemonDetail?.name == pair.first.name) {
                    val pokemonPair = Pair(pair.first, pokemonDetail)
                    pokemonPairList[index] = pokemonPair
                    notifyItemChanged(index)
                    return@forEach
                }
            }

        }
    }

    class ViewHolder(itemView: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private val view: ItemPokemonBinding = itemView
        fun bindView(item: Pair<PokemonListing, Pokemon?>, clickCallback: (Pokemon) -> Unit) {
            with(view) {
                val pokemonListing = item.first
                val pokemon = item.second
                when (pokemon) {
                    null -> {
                        itemPokeNameTv.text = pokemonListing.name?.capitalize()
                    }
                    else -> {
                        val color = PokemonColorUtil(itemView.context).getPokemonColor(pokemon?.types)
                        itemPokemonFrameLayout.background.colorFilter =
                            PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

                        itemPokeNameTv.text = pokemon.name?.capitalize()
                        itemPokeImageView.loadFromUrl(pokemon?.sprites?.other?.official_artwork?.front_default)
                        itemPokeIdTv.text = itemView.context.getString(R.string.text_id_pokemon,pokemon.id)
                        pokemon?.types?.forEachIndexed { index, pokeTypes ->
                            when (index) {
                                0 -> {
                                    itemPokeType1Tv.text = pokeTypes.type?.name?.capitalize()
                                    itemPokeType1Tv.visible()
                                }
                                1 ->{
                                    itemPokeType2Tv.text = pokeTypes.type?.name?.capitalize()
                                    itemPokeType2Tv.visible()
                                }
                                2 ->{
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

        fun loadPokemonInfo(pokemon: Pokemon?) {
            with(view) {
                itemPokeImageView.loadFromUrl(pokemon?.sprites?.front_default)
            }
        }
    }

}
