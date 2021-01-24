package com.gabriel.pokedex.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.domain.usecase.PokeApiUseCase
import com.gabriel.pokedex.core.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokedexViewModel (
    private val pokeApiUseCase: PokeApiUseCase
) : BaseViewModel() {

    private val _pokesList = MutableLiveData<Pair<List<PokemonListing>?, Int>>()
    val pokesList: LiveData<Pair<List<PokemonListing>?, Int>> = _pokesList
    private var offset: Int = 20 // Handles paging,
    // offset= 20 page 1, 40 page 2, 60 page 3 ...


    private val coroutinesContext = Dispatchers.IO

    fun fetchPokemonsList() {
        when (offset) {
            1 -> _loading.postValue(true)
            else -> _pageLoading.postValue(true)
        }
        viewModelScope.launch(coroutinesContext) {
            pokeApiUseCase.fetchPokemonsList(offset)
                .catch { e ->
                    handleFailure(e.message)
                    when (offset) {
                        1 -> _loading.postValue(false)
                        else -> _pageLoading.postValue(false)
                    }
                }
                .collect { repos ->
                    _pokesList.postValue(Pair(repos, offset))
                    when (offset++) {
                        1 -> _loading.postValue(false)
                        else -> _pageLoading.postValue(false)
                    }
                }
        }
    }

}