package com.gabriel.pokedex.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.domain.usecase.PokeApiUseCase
import com.gabriel.pokedex.core.extensions.empty
import com.gabriel.pokedex.core.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val pokeApiUseCase: PokeApiUseCase
) : BaseViewModel() {

    private val _pokesList = MutableLiveData<Pair<List<PokemonListing>?, Int>>()
    val pokesList: LiveData<Pair<List<PokemonListing>?, Int>> = _pokesList
    val _pokesDetailList = MutableLiveData<Pair<ArrayList<Pokemon>,Int>>()
    val pokesDetailList: LiveData<Pair<ArrayList<Pokemon>,Int>> = _pokesDetailList
    private val pokemonArray: ArrayList<Pokemon> = arrayListOf()
    private val pokemonPagingArray: ArrayList<Pokemon> = arrayListOf()
    private val errorIds = arrayListOf<String>()
    private val _pokemon = MutableLiveData<Pokemon>()
    private val pokemon: LiveData<Pokemon> = _pokemon
    private val PAGE_1 = 20 // Handles paging,
    private var offset: Int = PAGE_1 // Handles paging,
    // offset= 20 page 1, 40 page 2, 60 page 3 ...


    private val coroutinesContext = Dispatchers.IO

    fun fetchPokemonsList() {
        pokemonPagingArray.clear()
        errorIds.clear()
        when (offset) {
            PAGE_1 -> _loading.postValue(true)
            else -> _pageLoading.postValue(true)
        }
        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.fetchPokemonsList(offset)
                .catch { e ->
                    handleFailure(e.message)
                    when (offset) {
                        PAGE_1 -> _loading.postValue(false)
                        else -> _pageLoading.postValue(false)
                    }
                }
                .collect { pokes ->
                    handlePokemonsListAndGetPokemonsDetails(pokes)
//                    _pokesList.postValue(Pair(pokes, offset))
                }
        }
    }

    private fun handlePokemonsListAndGetPokemonsDetails(pokes: List<PokemonListing>?) {
        pokes?.forEach {
            val url = it.url
            val id = url?.substringAfterLast("https://pokeapi.co/api/v2/pokemon/")
                ?.replace("/", String.empty())
            getPokemonDetail(id.orEmpty())

        }
    }

    fun getPokemonDetail(id: String) {

        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.getPokemonDetail(id)
                .catch { e ->
                    handleFailure(e.message)
                    errorIds.add(id)
                    checkIfAllRequestsAreFinished()
                }
                .collect { poke ->
                    poke?.let {
                        if (pokemonArray.contains(it).not()) {
                            pokemonArray.add(it)
                        }
                        if (pokemonPagingArray.contains(it).not()) {
                            pokemonPagingArray.add(it)
                        }
                    }
                    checkIfAllRequestsAreFinished()
                }


        }
    }

    private fun checkIfAllRequestsAreFinished() {
        val errorCallbacks = errorIds.size
        val successCallbacks = pokemonPagingArray.size
        val totalCallbacks = errorCallbacks + successCallbacks
        val totalPokemonPageListing = 20
        if (totalCallbacks == totalPokemonPageListing) {

            when (offset) {
                PAGE_1 -> {
                    _pokesDetailList.postValue(Pair(pokemonArray, offset))
                    _loading.postValue(false)
                }
                else -> {
                    _pokesDetailList.postValue(Pair(pokemonPagingArray, offset))
                    _pageLoading.postValue(false)
                }
            }
            offset += 20
        }
    }
}