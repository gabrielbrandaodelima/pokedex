package com.gabriel.pokedex.features.ui.view.details

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.loadFromUrl
import com.gabriel.pokedex.core.extensions.viewBinding
import com.gabriel.pokedex.core.extensions.visibility
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentPokemonDetailsBinding
import com.gabriel.pokedex.features.ui.view.details.adapter.PokemonDetailsViewPagerAdapter
import com.gabriel.pokedex.features.util.PokemonColorUtil

class PokemonDetailsFragment : BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding by viewBinding(FragmentPokemonDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = arguments?.getParcelable<Pokemon>("pokemon")
        pokemon?.let {
            showPokemonDetails(it)
        }

    }

    private fun showPokemonDetails(pokemon: Pokemon) {
        binding?.apply {
            pokeImage.loadFromUrl(pokemon.sprites?.other?.official_artwork?.front_default)
            pokemonName.text = pokemon.name?.capitalize()
            pokemonId.text = getString(
                R.string.text_id_pokemon,
                pokemon.id.toString()
            )
            pokemon?.types?.apply {
                val color =
                    PokemonColorUtil(requireContext()).getPokemonColor(this)
                appBar.background.colorFilter =
                    PorterDuffColorFilter(color, PorterDuff.Mode.DST_ATOP)
                toolbarLayout.contentScrim?.colorFilter =
                    PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                activity?.window?.statusBarColor =
                    PokemonColorUtil(requireContext()).getPokemonColor(this)
                forEachIndexed { index, pokeTypes ->
                    when (index) {
                        0 -> {
                            pokeType1.text = pokeTypes.type?.name?.capitalize()
                            pokeType1.visibility(pokeTypes.type?.name.isNullOrBlank().not())
                        }
                        1 -> {
                            pokeType2.text = pokeTypes.type?.name?.capitalize()
                            pokeType2.visibility(pokeTypes.type?.name.isNullOrBlank().not())
                        }
                        2 -> {
                            pokeType3.text = pokeTypes.type?.name?.capitalize()
                            pokeType3.visibility(pokeTypes.type?.name.isNullOrBlank().not())
                        }
                    }
                }
            }
            val pager = pokemonViewPager
            val tabs = tabs
            pager.adapter =
                PokemonDetailsViewPagerAdapter(parentFragmentManager, requireContext(), pokemon)
            tabs.setupWithViewPager(pager)
        }
    }
}
