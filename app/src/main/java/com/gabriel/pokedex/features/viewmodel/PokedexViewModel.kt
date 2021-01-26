package com.gabriel.pokedex.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import com.gabriel.pokedex.core.domain.usecase.PokeApiUseCase
import com.gabriel.pokedex.core.extensions.empty
import com.gabriel.pokedex.core.platform.BaseViewModel
import com.gabriel.pokedex.core.platform.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val pokeApiUseCase: PokeApiUseCase
) : BaseViewModel() {

    private var nextPageOffset: String = String.empty()
    private val _pokesList = MutableLiveData<PokemonListingResponse?>()
    val pokesList get() = _pokesList
    val _pokesDetailList = SingleLiveEvent<Pair<ArrayList<Pokemon>, Int>>()
    val pokesDetailList: LiveData<Pair<ArrayList<Pokemon>, Int>> = _pokesDetailList
    private val pokemonArray: ArrayList<Pokemon> = arrayListOf()
    private val pokemonPagingArray: ArrayList<Pokemon> = arrayListOf()
    private val errorIds = arrayListOf<String>()
    private val _pokemonSearched = SingleLiveEvent<Pokemon?>()
    val pokemonSearched: LiveData<Pokemon?> = _pokemonSearched
    private val PAGE_SIZE = 20 // Handles paging,
    private var offset: Int = PAGE_SIZE // Handles paging,
    // offset= 20 page 1, 40 page 2, 60 page 3 ...


    val _postPokeSuccess = SingleLiveEvent<Boolean>()
    val postPokeSuccess: LiveData<Boolean> = _postPokeSuccess

    private val coroutinesContext = Dispatchers.IO

    fun getPokemonsList(): ArrayList<Pokemon> {
        return pokemonArray
    }

    fun fetchPokemonsList(refresh: Boolean = false) {
        if (refresh) {
            offset = PAGE_SIZE
            nextPageOffset = String.empty()
            pokemonArray.clear()
        }
        pokemonPagingArray.clear()
        errorIds.clear()
        when (offset) {
            PAGE_SIZE -> _loading.postValue(true)
            else -> _pageLoading.postValue(true)
        }
        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.executeFetchPokemonsList(offset)
                .catch { e ->
                    handleFailure(e.toString())
                    when (offset) {
                        PAGE_SIZE -> _loading.postValue(false)
                        else -> _pageLoading.postValue(false)
                    }
                }
                .collect { pokeResponse ->
                    handlePokemonsListAndGetPokemonsDetails(pokeResponse)
                    _pokesList.postValue(pokeResponse)
                }
        }
    }

    private fun handlePokemonsListAndGetPokemonsDetails(pokes: PokemonListingResponse?) {
        pokes?.apply {
            nextPageOffset = //get next page offset from API
                next?.substringAfterLast("offset=")?.substringBefore("&") ?: String.empty()
            results?.forEach {
                val url = it.url
                val id = url?.substringAfterLast("https://pokeapi.co/api/v2/pokemon/")
                    ?.replace("/", String.empty())
                getPokemonDetail(id.orEmpty())

            }

        }
    }

    fun getPokemonDetail(id: String) {

        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.executeGetPokemonDetail(id)
                .catch { e ->
                    handleFailure(e.toString())
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

    fun searchPokemonDetail(id: String) {

        _loading.postValue(true)
        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.executeGetPokemonDetail(id)
                .catch { e ->
                    handleFailure(e.toString())
                    _loading.postValue(false)
                }
                .collect { poke ->
                    _loading.postValue(false)
                    _pokemonSearched.postValue(poke)
                }


        }
    }

    fun postPokemon(pokemon: Pokemon) {

        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.executePostPokemon(pokemon)
                .catch { e ->
                    handleFailure(e.toString())
                    _postPokeSuccess.postValue(false)
                }
                .collect {
                    _postPokeSuccess.postValue(true)
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
                PAGE_SIZE -> {
                    _loading.postValue(false)
                }
                else -> {
                    _pageLoading.postValue(false)
                }
            }
            _pokesDetailList.postValue(Pair(pokemonPagingArray, offset))
            needsLoading.postValue(true)
            offset = nextPageOffset.toInt()
        }
    }
}