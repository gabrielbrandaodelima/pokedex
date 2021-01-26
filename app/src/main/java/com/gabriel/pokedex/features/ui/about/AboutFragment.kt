package com.gabriel.pokedex.features.ui.about

import android.os.Bundle
import android.view.View
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.viewBinding
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    val binding: FragmentAboutBinding by viewBinding(FragmentAboutBinding::bind)
    companion object {
        @JvmStatic
        fun newInstance(pokemon: Pokemon?) = AboutFragment().apply {
            arguments = Bundle().apply {
                putParcelable("pokemon", pokemon)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = arguments?.getParcelable<Pokemon>("pokemon")
        binding?.apply {
            pokemon?.apply {
                pokemonWeightTv.text = weight.toString().plus(getString(R.string.suffix_hm))
                pokemonHeightTv.text = height.toString().plus(getString(R.string.suffix_dm))
                pokemonBaseXp.text = base_experience.toString()
            }
        }
    }
}
