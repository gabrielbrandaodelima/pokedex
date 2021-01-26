package com.gabriel.pokedex.features.ui.moves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.setUpRecyclerView
import com.gabriel.pokedex.core.extensions.viewBinding
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentAboutBinding
import com.gabriel.pokedex.databinding.FragmentMovesBinding
import com.gabriel.pokedex.features.ui.moves.adapter.MovesAdapter

class MovesFragment : BaseFragment(R.layout.fragment_moves) {


    val binding: FragmentMovesBinding by viewBinding(FragmentMovesBinding::bind)
    companion object {
        @JvmStatic
        fun newInstance(pokemon: Pokemon?) = MovesFragment().apply {
            arguments = Bundle().apply {
                putParcelable("pokemon", pokemon)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPokemonMoves()
    }

    private fun setPokemonMoves() {
        val pokemon = arguments?.getParcelable<Pokemon>("pokemon")
        pokemon?.apply {
            binding?.apply {
                pokemonMovesRecyclerView?.setUpRecyclerView(requireContext(),{
                    val adapter = MovesAdapter(moves)
                    it.adapter = adapter
                })
            }
        }
    }

}
