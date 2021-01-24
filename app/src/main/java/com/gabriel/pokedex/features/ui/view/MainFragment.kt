package com.gabriel.pokedex.features.ui.view

import android.os.Bundle
import android.view.View
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.extensions.observe
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.features.viewmodel.PokedexViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel
class MainFragment : BaseFragment(R.layout.fragment_main) {



    val viewModel: PokedexViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            observe(pokesList,::handleSuccessPokemonsList)
        }
        viewModel.fetchPokemonsList()

    }

    private fun handleSuccessPokemonsList(pair: Pair<List<PokemonListing>?, Int>?) {

    }


}