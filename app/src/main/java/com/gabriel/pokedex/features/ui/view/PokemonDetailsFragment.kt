package com.gabriel.pokedex.features.ui.view

import android.os.Bundle
import android.view.View
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.viewBinding
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentDetailsBinding

class PokemonDetailsFragment : BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)

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

            pokemon?.types?.forEachIndexed { index, pokeTypes ->
                when (index) {
                    0 -> {
                        pokeType1.text = pokeTypes.type?.name?.capitalize()
                        pokeType1.visible()
                    }
                    1 ->{
                        pokeType2.text = pokeTypes.type?.name?.capitalize()
                        pokeType2.visible()
                    }
                    2 ->{
                        pokeType3.text = pokeTypes.type?.name?.capitalize()
                        pokeType3.visible()
                    }
                }
            }

        }
    }
}
