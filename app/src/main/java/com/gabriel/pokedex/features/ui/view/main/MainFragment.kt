package com.gabriel.pokedex.features.ui.view.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.extensions.*
import com.gabriel.pokedex.core.platform.BaseFragment
import com.gabriel.pokedex.databinding.FragmentMainBinding
import com.gabriel.pokedex.features.ui.view.main.adapter.PokemonAdapter
import com.gabriel.pokedex.features.viewmodel.PokedexViewModel

import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {


    private val binding by viewBinding(FragmentMainBinding::bind)

    private var needsLoading = MutableLiveData(false)
    private var pokesList: List<Pokemon>? = null
    private var searchView: SearchView? = null

    val viewModel: PokedexViewModel by viewModel()
    private var pokemonAdapter: PokemonAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initViewModel()
        setSearchClickListener()

    }

    private fun setSearchClickListener() {
        binding?.searchView.setOnQueryTextListener(this)
        binding.layoutSearchBar?.setOnClickListener {
            handleSearchClick()
        }
    }

    private fun handleSearchClick() {
        binding?.apply {
            if (searchView.isVisible()) {
                hideSearchView()
            } else {
                showSearchView()
            }
        }
    }

    fun showSearchView() {
        binding?.apply {
            searchImgview.gone()
            searchTxtview.gone()
            searchView.visible()
        }
    }

    fun hideSearchView() {
        binding?.apply {
            searchView.gone()
            searchImgview.visible()
            searchTxtview.visible()
        }

    }

    private fun setUpRecyclerView() {

        binding.pokedexRecyclerView?.apply {
            setUpRecyclerView(requireContext(), {
                pokemonAdapter = PokemonAdapter(arrayListOf(), ::handlePokemonClicked)
                adapter = pokemonAdapter
            })

            setUpPaging(needsLoading) {
                viewModel.fetchPokemonsList()
            }
        }

    }

    private fun handlePokemonClicked(pokemon: Pokemon?) {

        findNavController().navigate(R.id.pokemonDetailsFragment, Bundle().apply {
            putParcelable("pokemon", pokemon)
        })

    }

    private fun initViewModel() {
        viewModel.apply {
//            observe(pokesList, ::handleSuccessPokemonsList)
            observe(pokesDetailList, ::handleSuccessPokemonsList)
            observe(pageLoading, {
                it?.let { it1 -> managePageProgress(it1) }
            })
            observe(loading, {
                it?.let { it1 -> manageProgress(it1) }
            })
            failure(failure, ::handleFailure)
        }
        viewModel.fetchPokemonsList()
    }

    var count = 0
    private fun loadPokemonImagesInList(arrayList: java.util.ArrayList<Pokemon>?) {
        Toast.makeText(
            requireContext(),
            "finished: ${arrayList?.size} - ${count++} ",
            Toast.LENGTH_LONG
        ).show()
//        if (arrayList?.size ?: 0 > 0)
//            pokemonAdapter?.loadPokemonsInfo(arrayList)
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

    private fun handleSuccessPokemonsList(pair: Pair<List<Pokemon>?, Int>?) {
        needsLoading.postValue(true)
        val list = pair?.first
        val page = pair?.second
        if (page == 20) {
            pokesList = list
            pokemonAdapter?.addAll(list as ArrayList<Pokemon>)
        } else {

            list?.toMutableList()?.let { pokesList?.toMutableList()?.addAll(it) }
            pokemonAdapter?.appendAll(list as ArrayList<Pokemon>)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        hideSearchView()
        return true
    }


}