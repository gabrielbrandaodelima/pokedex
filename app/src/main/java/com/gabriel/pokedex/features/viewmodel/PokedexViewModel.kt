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
    val _pokesDetailList = MutableLiveData<ArrayList<Pokemon>>()
    val pokesDetailList: LiveData<ArrayList<Pokemon>> = _pokesDetailList
    private val _pokemon = MutableLiveData<Pokemon>()
    private val pokemon: LiveData<Pokemon> = _pokemon
    private val PAGE_1 = 20 // Handles paging,
    private var offset: Int = PAGE_1 // Handles paging,
    // offset= 20 page 1, 40 page 2, 60 page 3 ...


    private val coroutinesContext = Dispatchers.IO

    init {
        _pokesDetailList.postValue(arrayListOf())
    }
    fun fetchPokemonsList() {
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
                    _pokesList.postValue(Pair(pokes, offset))
                    when (offset) {
                        PAGE_1 -> _loading.postValue(false)
                        else -> _pageLoading.postValue(false)
                    }
                    offset += 20
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
                }
                .collect { poke ->

                    poke?.let { _pokesDetailList.value?.add(it) }
                    _pokesDetailList.postValue(_pokesDetailList.value)
                    _pokemon.postValue(poke)
                }


        }
    }
}