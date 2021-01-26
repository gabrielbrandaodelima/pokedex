package com.gabriel.pokedex.features.ui.about

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.platform.BaseFragment

class AboutFragment : BaseFragment(R.layout.fragment_about) {

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
        val pokemon = arguments?.getString("pokemon")
        pokemon?.apply {

        }
    }
}
