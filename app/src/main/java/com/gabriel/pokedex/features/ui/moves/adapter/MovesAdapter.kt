package com.gabriel.pokedex.features.ui.moves.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.pokedex.core.domain.model.response.Move
import com.gabriel.pokedex.databinding.LayoutItemMovesBinding

class MovesAdapter(val list: List<Move>?) : RecyclerView.Adapter<MovesAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LayoutItemMovesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    class ItemViewHolder(val binding: LayoutItemMovesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(move: Move?) {
            binding.itemMoveName.text = move?.move?.name
        }

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val move = list?.get(position)
        holder.bindView(move)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}
