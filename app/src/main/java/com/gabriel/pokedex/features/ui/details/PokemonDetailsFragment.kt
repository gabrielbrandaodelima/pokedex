package com.gabriel.pokedex.features.ui.details

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.*
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentPokemonDetailsBinding
import com.gabriel.pokedex.features.ui.details.adapter.PokemonDetailsViewPagerAdapter
import com.gabriel.pokedex.features.util.PokemonColorUtil
import com.gabriel.pokedex.features.viewmodel.PokedexViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailsFragment : BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding by viewBinding(FragmentPokemonDetailsBinding::bind)


    val viewModel: PokedexViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = arguments?.getParcelable<Pokemon>("pokemon")
        pokemon?.let {
            showPokemonDetails(it)
        }
        viewModel?.apply {
            observe(postPokeSuccess, ::handlePostPokemonCallback)
        }
    }

    private fun handlePostPokemonCallback(saved: Boolean?) {
        if (saved == true) {
            binding.pokeFavoriteStar?.visible()
            Toast.makeText(
                requireContext(),
                getString(R.string.text_pokemon_saved),
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            Toast.makeText(
                requireContext(),
                "Internal error, please try again",
                Toast.LENGTH_LONG
            )
                .show()

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

            savePokeFavoriteButton?.setOnClickListener {
                viewModel.postPokemon(pokemon)
            }

        }
    }
}
