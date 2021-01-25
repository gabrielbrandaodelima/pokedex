package com.gabriel.pokedex.features.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.extensions.*
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentMainBinding
import com.gabriel.pokedex.features.ui.adapter.PokemonAdapter
import com.gabriel.pokedex.features.viewmodel.PokedexViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel
class MainFragment : BaseFragment(R.layout.fragment_main) {


    private val binding by viewBinding(FragmentMainBinding::bind)
    val viewModel: PokedexViewModel by viewModel()

    private var needsLoading = MutableLiveData(true)
    private var pokesList: List<PokemonListing>? = null

    private var pokemonAdapter: PokemonAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initViewModel()


    }

    private fun setUpRecyclerView() {

        binding.pokedexRecyclerView?.apply {
            setUpRecyclerView(requireContext(),{
                pokemonAdapter = PokemonAdapter(arrayListOf(),::handlePokemonClicked)
                adapter = pokemonAdapter
            })

            setUpPaging(needsLoading) {
                viewModel.fetchPokemonsList()
            }
        }

    }

    private fun handlePokemonClicked(pokemon: Pokemon?) {


    }

    private fun initViewModel() {
        viewModel.apply {
            observe(pokesList,::handleSuccessPokemonsList)
            observe(pokesDetailList,::loadPokemonImagesInList)
            observe(pageLoading, {
                it?.let { it1 -> managePageProgress(it1) }
            })
            observe(loading, {
                it?.let { it1 -> manageProgress(it1) }
            })
            failure(failure,::handleFailure)
        }
        viewModel.fetchPokemonsList()
    }

    private fun loadPokemonImagesInList(arrayList: java.util.ArrayList<Pokemon>?) {
        pokemonAdapter?.loadPokemonsInfo(arrayList)
    }


    private fun manageProgress(loading: Boolean) {
        if (loading) {
            binding.pokedexRecyclerView.gone()
            binding.recyclerProgress.visible()
        } else {
            binding.pokedexRecyclerView.visible()
            binding.recyclerProgress.gone()
        }

    }

    private fun managePageProgress(loading: Boolean) {
        binding.pagingProgress.visibility(loading)
    }

    private fun handleSuccessPokemonsList(pair: Pair<List<PokemonListing>?, Int>?) {
        needsLoading.postValue(true)
        val list = pair?.first
        val page = pair?.second
        if (page == 20) {
            pokesList = list
            pokemonAdapter?.addAll(list as ArrayList<PokemonListing>)
        } else {

            list?.toMutableList()?.let { pokesList?.toMutableList()?.addAll(it) }
            pokemonAdapter?.appendAll(list as ArrayList<PokemonListing>)
        }
    }


}