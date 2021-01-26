package com.gabriel.pokedex.features.ui.stats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.pokedex.core.domain.model.response.Move
import com.gabriel.pokedex.core.domain.model.response.Stats
import com.gabriel.pokedex.core.extensions.capitalizeWords
import com.gabriel.pokedex.databinding.LayoutItemMovesBinding
import com.gabriel.pokedex.databinding.LayoutItemStatsBinding

class StatsAdapter(val list: List<Stats>?) : RecyclerView.Adapter<StatsAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutItemStatsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    class ItemViewHolder(val binding: LayoutItemStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(stats: Stats?) {
            binding.apply {
                pokemonStatsNameTv.text = stats?.stat?.name?.toUpperCase()
                pokemonStatsValueTv.text = stats?.base_stat?.toString()
                pokemonStatsProgress?.progress = stats?.base_stat ?: 0

            }
        }

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val stats = list?.get(position)
        holder.bindView(stats)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

}
