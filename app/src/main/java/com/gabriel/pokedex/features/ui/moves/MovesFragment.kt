package com.gabriel.pokedex.features.ui.moves

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon

class MovesFragment : Fragment(R.layout.fragment_moves) {
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

        val pokemon = arguments?.getString("pokemon")
        pokemon?.apply {

        }
    }

}
