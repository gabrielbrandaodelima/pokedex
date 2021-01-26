package com.gabriel.pokedex.features.ui.stats

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.setUpRecyclerView
import com.gabriel.pokedex.core.extensions.viewBinding
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentMovesBinding
import com.gabriel.pokedex.databinding.FragmentStatsBinding
import com.gabriel.pokedex.features.ui.stats.adapter.StatsAdapter

class StatsFragment : BaseFragment(R.layout.fragment_stats) {

    val binding: FragmentStatsBinding by viewBinding(FragmentStatsBinding::bind)
    companion object {
        @JvmStatic
        fun newInstance(pokemon: Pokemon?) = StatsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("pokemon", pokemon)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPokemonStats()


    }

    private fun setPokemonStats() {
        val pokemon = arguments?.getParcelable<Pokemon>("pokemon")
        pokemon?.apply {
            binding?.apply {
                pokemonStatsRecyclerView?.apply {
                    setUpRecyclerView(requireContext(),{

                        it.adapter = StatsAdapter(stats)
                    })
                }
            }
        }
    }
}
