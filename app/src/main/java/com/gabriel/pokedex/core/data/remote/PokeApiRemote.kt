package com.gabriel.pokedex.core.data.remote

import com.gabriel.pokedex.core.data.ApiPokeApi
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeApiRemote {

    fun fetchPokemonsList(offset: Int): Flow<List<PokemonListing>?> {
        return flow {
            try {
                val pokes = ApiPokeApi.apiService.fetchPokemonsList(offset = offset)
                emit(pokes.results)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(null)
            }
        }
    }

    fun getPokemonDetail(id: String): Flow<Pokemon?> {
        return flow {
            try {
                val pokemon = ApiPokeApi.apiService.getPokemonDetail(id)
                emit(pokemon)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(null)
            }
        }
    }
}