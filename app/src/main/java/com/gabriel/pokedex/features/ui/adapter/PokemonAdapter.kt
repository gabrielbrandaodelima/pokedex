package com.gabriel.pokedex.features.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.databinding.ItemPokemonBinding

class PokemonAdapter(
    private var pokemonList: ArrayList<PokemonListing>,
    private var clickCallback: (PokemonListing) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPokemonBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemonList[position]
        holder.bindView(item, clickCallback)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    internal fun addAll(list: ArrayList<PokemonListing>) {
        pokemonList = arrayListOf()
        pokemonList = list
        notifyDataSetChanged()
    }

    internal fun appendAll(linksList: ArrayList<PokemonListing>) {
        val startindex = pokemonList.size
        pokemonList.addAll(startindex, linksList)
        notifyItemRangeInserted(startindex, linksList.size)
    }

    fun clear() {
        pokemonList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private val view: ItemPokemonBinding = itemView
        fun bindView(item: PokemonListing, clickCallback: (PokemonListing) -> Unit) {
            with(view) {

                itemPokeNameTv.text = item.name
                itemPokemonFrameLayout.setOnClickListener { clickCallback(item) }
            }


        }
    }

}
