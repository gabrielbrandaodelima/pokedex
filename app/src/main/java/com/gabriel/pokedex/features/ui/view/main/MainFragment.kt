package com.gabriel.pokedex.features.ui.view.main

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.di.viewModelsModule
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

    private var pokesList: List<Pokemon>? = null
    private var searchView: SearchView? = null

    val viewModel: PokedexViewModel by viewModel()
    private var pokemonAdapter: PokemonAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokesList = viewModel.getPokemonsList()
        setUpRecyclerView()
        observeViewModel()
        setSearchClickListener()
        setSwipeRefreshListener()
        if (viewModel.getPokemonsList().isNullOrEmpty())
            viewModel.fetchPokemonsList()

    }

    private fun setSwipeRefreshListener() {
        binding.pokeSwipeRefreshLayout?.setOnRefreshListener {
            viewModel.fetchPokemonsList(true)
        }
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
                pokemonAdapter = PokemonAdapter(pokesList as ArrayList<Pokemon?>, ::handlePokemonClicked)
                adapter = pokemonAdapter
            })

            setUpPaging(viewModel.needsLoading) {
                viewModel.fetchPokemonsList()
            }
        }

    }

    private fun handlePokemonClicked(pokemon: Pokemon?) {

        findNavController().navigate(R.id.pokemonDetailsFragment, Bundle().apply {
            putParcelable("pokemon", pokemon)
        })

    }

    private fun observeViewModel() {
        viewModel.apply {
//            observe(pokesList, ::handleSuccessPokemonsList)
            observe(pokesDetailList, ::handleSuccessPokemonsList)
            observe(pokemonSearched, ::handleSuccessSearch)
            observe(pageLoading, {
                it?.let { it1 -> managePageProgress(it1) }
            })
            observe(loading, {
                it?.let { it1 -> manageProgress(it1) }
            })
            failure(failure, ::handleFailure)
        }

    }


    private fun manageProgress(loading: Boolean) {
        if (binding.pokeSwipeRefreshLayout.isRefreshing.not())
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

        binding.pokeSwipeRefreshLayout.isRefreshing = false

        val list = pair?.first
        val page = pair?.second
        if (page == 20) {
            pokesList = list
            pokemonAdapter?.addAll(list as ArrayList<Pokemon?>)
        } else {

            list?.toMutableList()?.let { pokesList?.toMutableList()?.addAll(it) }
            pokemonAdapter?.appendAll(list as ArrayList<Pokemon>)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            "recycler",
            binding.pokedexRecyclerView.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val recyclerStateBundle = savedInstanceState?.getParcelable<Parcelable>("recycler")
        recyclerStateBundle?.let {
            binding?.pokedexRecyclerView?.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.searchPokemonDetail(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filter(newText)
        return false
    }

    override fun onClose(): Boolean {
        hideSearchView()
        resetAdapter()
        return true
    }

    private fun filter(query: String?) {
        if (query != null && query.isNotEmpty()) {
            val filtered =
                pokemonAdapter?.pokemonArray?.filter { it?.name?.contains(query) == true }
            (filtered as? ArrayList<Pokemon?>)?.let {
                pokemonAdapter?.addAll(it)
            }
        } else
            resetAdapter()
    }

    private fun resetAdapter() {
        (pokesList as? ArrayList<Pokemon?>)?.let {
            pokemonAdapter?.addAll(it)
        }
    }

    fun handleSuccessSearch(pokemon: Pokemon?) {
        pokemon?.let {
            if (pokesList?.contains(it)?.not() == true)
                pokesList?.toMutableList()?.add(it)
            if (pokemonAdapter?.pokemonArray?.contains(it)?.not() == true)
                pokemonAdapter?.appendAll(arrayListOf(pokemon))
            filter(it.name)
        }
    }


}
